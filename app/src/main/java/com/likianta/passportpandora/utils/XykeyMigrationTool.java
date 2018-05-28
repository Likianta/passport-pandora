package com.likianta.passportpandora.utils;


import android.content.Context;

import com.google.gson.Gson;
import com.likianta.passportpandora.R;
import com.likianta.passportpandora.beans.Account;
import com.likianta.passportpandora.beans.AccountPrimary;
import com.likianta.passportpandora.beans.AccountSecondary;
import com.likianta.passportpandora.beans.AccountXyFormat;
import com.likianta.passportpandora.beans.GroupItem;
import com.likianta.passportpandora.methods.NewGroupGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */
public class XykeyMigrationTool {
    
    //  # Work Flow (Outline)
    //
    //  ## Stage 1
    //
    //  1. getOriginalFileData() - obtain the original text content from xykey backup file (Json
    //  format)
    //  2. filterFileData() (simplyTrimFileData()) - the original text is not compatible to load
    //  directly, so it needs a "filter" operation to be an easy-to-use format
    //  3. convertDataIntoXyFormat() - convert this filtered data into AccountXyFormat beans
    //
    //  ## Stage 2
    //
    //  1. getXyFormat() - a simple getter method, just return AccountXyFormat beans
    //  2. processXyFormat() - process this bean list, such as integrating extra values into single
    //   string and so on, it aims to return an easy-to-use format for next step
    //  3. convertXyFormatIntoAccount() - convert AccountXyFormat into AccountPrimary,
    //  AccountSecondary and AccountTertiary formats, in detailed it looks like this:
    //      1. XyFormat(name, account, password) --> AccountPrimary(title, username, password)
    //      2. XyFormat(password2, url, note) --> AccountSecondary(object {...})
    //      3. XyFormat(extra {...}) --> AccountTertiary(object {...})
    //
    //  ## Stage 3
    //
    //  1. integrateAccountsIntoOne() - integrate AccountPrimary, Secondary, Tertiary into one
    //  ACCOUNT bean
    //  2. getAccount() - return this Account bean
    
    private Context context;
    
    public XykeyMigrationTool(Context context) {
        this.context = context;
    }
    
    public List<Account> getAccountList() {
        
        // TODO test filePath
        String filePath = "/storage/emulated/0/Documents/test_xykey_20180501.txt";
//            String filePath = "/storage/emulated/0/Documents/test_xykey_20180501_2338.txt";
        
        String[] fileData = beforeGetXyFormatData(filePath);
        
        List<AccountXyFormat> xyFormatData = getXyFormatData(fileData);
        
        return convertXyFormatToAccount(xyFormatData);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // before get xykey format data, we should get file content
    
    private String[] beforeGetXyFormatData(String filePath) {
        String content;
        try {
            content = getOriginalFileData(filePath);
            return simplyTrimFileData(content);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Read file data
     * https://www.cnblogs.com/tianzhijiexian/p/4246497.html
     *
     * @param filePath the full file path, e.g. "/storage/emulated/0/pms/test.xml"
     * @return String fileData
     */
    private String getOriginalFileData(String filePath) throws IOException {
        String originalFileData = null;
        FileInputStream inputStream = new FileInputStream(filePath.trim());
        
        // TODO TEST: estimated as 1kb (1024bytes) size
        byte[] tempBytes = new byte[1024];
        // TODO TEST: estimated as 100kb (0.1mb) size
//        byte[] tempBytes = new byte[102400];
        
        if (0 < inputStream.read(tempBytes)) {
            originalFileData = new String(tempBytes);
        }
        
        return originalFileData;
    }
    
    private String[] simplyTrimFileData(String originalFileData) throws JSONException {
        // learn: https://stackoverflow
        // .com/questions/39177871/classcastexception-jsonarray-cannot-be-cast-to-java-lang-string
        JSONObject jsonObj = new JSONObject(originalFileData);
        JSONArray jsonArray = jsonObj.getJSONArray("key");
        
        String[] xykeyData = new String[jsonArray.length()];
        for (int i = 0, j = jsonArray.length(); i < j; i++) {
            xykeyData[i] = jsonArray.getString(i).trim();
        }
//        Logger.d("the final xykeyData is " + xykeyData[jsonArray.length() - 1]);
        return xykeyData;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // start to get xykey format data
    
    private List<AccountXyFormat> getXyFormatData(String[] fileData) {
        return convertFileDataToXyFormat(fileData);
    }
    
    private List<AccountXyFormat> convertFileDataToXyFormat(String[] fileData) {
        List<AccountXyFormat> xyFormatList = new ArrayList<>();
        AccountXyFormat xyFormat;
        for (String data : fileData) {
            xyFormat = new Gson().fromJson(data,
                    AccountXyFormat.class);
            xyFormatList.add(xyFormat);
        }
        return xyFormatList;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // convert xykey format to account beans and return this account list
    
    private List<Account> convertXyFormatToAccount(List<AccountXyFormat> xyFormatData) {
        AccountXyFormat xyFormat;
        AccountPrimary primary;
        AccountSecondary secondary;
        
        int n = xyFormatData.size();
        String note;
        
        List<Account> accountList = new ArrayList<>(n);
        List<AccountPrimary> primaryList = new ArrayList<>(n);
//        List<AccountSecondary> secondaryList = new ArrayList<>(n);
        
        // how to convert?
        //
        // AccountXyFormat --> Account(Primary, Secondary)
        // name --> title (accountTitle)
        // account --> username
        // password --> password
        //
        // password2 --> [note] "password2: " + password2 + "\n";
        // url --> [note] "url: " + url + "\n";
        // note --> [note] "note: " + note + "\n";
        // extra --> [note] "extra: \n" + Arrays.toString(extra);
        
        for (int i = 0; i < n; i++) {
            xyFormat = xyFormatData.get(i);
            
            primary = new AccountPrimary(
                    xyFormat.getName(),
                    xyFormat.getAccount(),
                    xyFormat.getPassword()
            );
            
            note = "password2: " + xyFormat.getPassword2() + "\n"
                    + "url: " + xyFormat.getUrl() + "\n"
                    + "note: " + xyFormat.getNote() + "\n"
                    + "extra: \n" + Arrays.toString(xyFormat.getExtra()
            );
            
            secondary = new AccountSecondary(
                    context.getResources().getString(R.string.default_group_name),
                    note
            );
            
            primaryList.add(primary);
//            secondaryList.add(secondary);
            accountList.add(new Account(primary, secondary));
        }
        
        createDataBase(accountList, primaryList);
        
        return accountList;
    }
    
    private void createDataBase(List<Account> accountList, List<AccountPrimary> primaryList) {
        // persist your data in realm transaction
        // REFERENCE: 2018-05-13
        // https://realm.io/docs/java/latest/#
        
        Realm realm = Realm.getDefaultInstance();
        
        realm.beginTransaction();
    
        // TODO TEST
        // clear out the existed database then rebuild
        realm.deleteAll();
        
        // the difference between copyToRealmOrUpdate and insertOrUpdate
        // REFERENCE: 2018-05-15
        // https://stackoverflow.com/questions/41953956/realm-order-of-insert-or-update
        realm.copyToRealmOrUpdate(accountList);
        
        int n = primaryList.size();
        RealmList<GroupItem> itemList = new RealmList<>();
        for (int i = 0; i < n; i++) {
            itemList.add(new GroupItem(
                    primaryList.get(i).getTitle(),
                    primaryList.get(i).getUsername()));
        }
        
        NewGroupGenerator.generateAll(
                context.getResources().getString(R.string.default_group_name),
                itemList
        );
        
        realm.commitTransaction();
    }
    
}
