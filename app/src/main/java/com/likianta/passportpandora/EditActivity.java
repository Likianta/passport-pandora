package com.likianta.passportpandora;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.likianta.passportpandora.beans.Account;
import com.likianta.passportpandora.beans.AccountPrimary;
import com.likianta.passportpandora.beans.AccountSecondary;
import com.likianta.passportpandora.beans.GroupHeader;
import com.likianta.passportpandora.data.TempNewAccount;
import com.likianta.passportpandora.frag.GroupFragment;
import com.likianta.passportpandora.methods.CardMotion;
import com.likianta.passportpandora.utils.KeyboardManager;
import com.orhanobut.logger.Logger;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Likianta_Dodoora on 2018/4/30 0030.
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    
    // ----------------------------------------------------------------
    // 这位哥写得特别好，相机、相册、裁剪这三大块都是看他的
    // https://blog.csdn.net/Hacker_ZhiDian/article/details/71075921
    // TODO UPDATE: 未来会将照片的方法提取到单独的一个类里
    
    // Request code
    private static final int TAKE_PHOTO_PERMISSION_REQUEST_CODE = 0; // 拍照的权限处理返回码
    private static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1; // 读储存卡内容的权限处理返回码
    private static final int TAKE_PHOTO_REQUEST_CODE = 2; // 拍照返回的 requestCode
    private static final int CHOICE_FROM_ALBUM_REQUEST_CODE = 3; // 相册选取返回的 requestCode
    private static final int CROP_PHOTO_REQUEST_CODE = 4; // 裁剪图片返回的 requestCode
    
    // ----------------------------------------------------------------
    
    // fragments
    private GroupFragment fragGroup = new GroupFragment();
    
    // Animation related
    private CardMotion cardMotion;
    
    // Photo process
    private Uri photoUri = null;
    private Uri photoOutputUri = null; // 图片最终的输出文件的 Uri
    
    // ----------------------------------------------------------------
    
    // layout views
    private Button btnDone;
    private Button btnSave;
    private CircleImageView btnPhoto;
    private EditText edtTitle;
    private EditText edtUsername;
    private EditText edtPassword;
    private ImageView bgCover;
    private RelativeLayout cardGroup;
    private RelativeLayout cardMemoActive;
    private RelativeLayout cardPortrait;
    private RelativeLayout cardSocialLogin;
    private TextView cardMemo;
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    @Override
    // bind views
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        
        initBinding();
        
        TempNewAccount.init(this);
        
    }
    
    @Override
    // when views created over, we can add listeners
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        
        addListeners();
    }
    
    @Override
    // show keyboard onResume
    public void onResume() {
        super.onResume();
        
        // Pulls up soft keyboard
        // TODO test
//        KeyboardManager.showKeyboard(this, edtTitle);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // onCreate()
    
    private void initBinding() {
        bgCover = findViewById(R.id.new_bg_cover);
        
        btnDone = findViewById(R.id.card_memo_btn_done);
        btnPhoto = findViewById(R.id.new_btn_photo);
        btnSave = findViewById(R.id.new_btn_save);
        
        cardGroup = findViewById(R.id.card_group);
        cardMemo = findViewById(R.id.card_memo);
        cardMemoActive = findViewById(R.id.card_memo_active);
        cardPortrait = findViewById(R.id.card_portrait);
        cardSocialLogin = findViewById(R.id.card_social_login);
        
        edtPassword = findViewById(R.id.new_edt_password);
        edtTitle = findViewById(R.id.new_edt_title);
        edtUsername = findViewById(R.id.new_edt_username);
        
        // TODO DELETE
        // Card motion binding
        cardMotion = new CardMotion(bgCover);
    }
    
    
    private void addListeners() {
        bgCover.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        cardGroup.setOnClickListener(this);
        cardMemo.setOnClickListener(this);
        cardSocialLogin.setOnClickListener(this);
        cardPortrait.setOnClickListener(this);
        
        // REFERENCE: 2018-05-04
        // https://blog.csdn.net/u011993368/article/details/45075097
//        newCardMemoActive.setOnClickListener(this);
    }
    
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // ----------------------------------------------------------------
            case R.id.new_btn_photo:
                checkUserPermission();
                openAlbum();
                break;
            // ----------------------------------------------------------------
            case R.id.new_btn_save:
                beforeSave();
                boolean result = onSave();
                afterSave(result);
                break;
            
            
            // ----------------------------------------------------------------
            case R.id.card_group:
                showFragment(fragGroup);
                break;
            // ----------------------------------------------------------------
            case R.id.card_memo:
                beforeCardMotion();
                onCardMotion();
                afterCardMotion();
                break;
            // ----------------------------------------------------------------
            case R.id.new_bg_cover:
                // Fall down to do retrieve motions
            case R.id.card_memo_btn_done:
                /*retrieveAnimator(cardMemoActive, cardMemo);*/
                beforeRetrieveMotion();
                onRetrieveMotion();
                afterRetrieveMotion();
                break;
            // ----------------------------------------------------------------
            case R.id.card_social_login:
                RelativeLayout target = findViewById(R.id.card_social_login_active);
                cardMotion.activeCard(1, target, view);
                break;
        }
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        
        transaction.add(R.id.edit_frag_group, fragment);
        
        transaction.commit();
    }
    
    public void removeFragment(Fragment fragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        
        transaction.remove(fragment);
        transaction.commit();
        
        // then show what you selected
        ((Button) cardGroup.findViewWithTag("button1")).setText(TempNewAccount.groupName);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    private void beforeRetrieveMotion() {
        beforeCardMotion();
        
        // Hide keyboard
        KeyboardManager.hideKeyboard(this);
    }
    
    private void onRetrieveMotion() {
        /* NOTICE: 2018-05-07
         * 由于现有的CardMotion体系与文字缩放动画不兼容，所以下面单独对文字动画进行处理 */
        if (cardMotion.getCurrentIndex() == 0) {
            EditText cardMemoEdt = findViewById(R.id.card_memo_edt);
//            cardMemoEdt.setAlpha(0.0f);
            ObjectAnimator animator = ObjectAnimator.ofFloat(cardMemoEdt, "alpha", 1.0f, 0.0f);
            animator.setDuration(50);
            animator.start();
        }
        cardMotion.deactivateCard(cardMemoActive, cardMemo);
        
    }
    
    private void afterRetrieveMotion() {
        // Show text
        EditText editText = findViewById(R.id.card_memo_edt);
        cardMemo.setText(editText.getText().toString());
        
        // Visibility setting
        cardMotion.getAnimatorSet().addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bgCover.setVisibility(View.GONE);
                cardMemoActive.setVisibility(View.GONE);
                cardMemo.setVisibility(View.VISIBLE);
            }
        });
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    private void beforeCardMotion() {
        // Visibility setting
        bgCover.setVisibility(View.VISIBLE);
        cardMemo.setVisibility(View.INVISIBLE);
        cardMemoActive.setVisibility(View.VISIBLE);
        
        TextView cardMemoHeading = findViewById(R.id.card_memo_heading);
//        EditText cardMemoEdt = findViewById(R.id.card_memo_edt);
        cardMotion.setCardMemoExtra(cardMemoHeading, btnDone);
    }
    
    private void onCardMotion() {
        cardMotion.activeCard(0, cardMemoActive, cardMemo);
        
        /* NOTICE: 2018-05-07
         * 由于现有的CardMotion体系与文字缩放动画不兼容，所以下面单独对文字动画进行处理 */
        
        EditText cardMemoEdt = findViewById(R.id.card_memo_edt);
        cardMemoEdt.setAlpha(0.0f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(cardMemoEdt, "alpha", 0.0f, 1.0f);
        animator.setDuration(50);
        animator.setStartDelay(450);
        animator.start();
        
    }
    
    private void afterCardMotion() {
        // Show keyboard
        final EditText editText = findViewById(R.id.card_memo_edt);
        KeyboardManager.showKeyboard(this, editText, 50);
        
        // Show text
        editText.setText((cardMemo).getText().toString());
        btnDone.setOnClickListener(this);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // when you clicked "save" button >>
    
    private void beforeSave() {
        // before onSave, we should shut down the keyboard
        KeyboardManager.hideKeyboard(this);
    }
    
    private boolean onSave() {
        // work flow
        //
        // onSave
        // -> checkValidation
        // -> (true) generateAccount
        // -> generateGroupItem
        // -> jumpToMainActivity
        // -> updateGroupItemList
        
        if (saveAccountPrimary()) {
            saveAccountSecondary();
        } else {
            return false;
        }
        
        return persistAccount();
    }
    
    private void afterSave(boolean result) {
        if (result) {
            Intent intent = new Intent();
            intent.putExtra("updated", true);
            setResult(RESULT_OK, intent);
            finish();
        } // else do nothing
    }
    
    // ----------------------------------------------------------------
    // the child methods of saving
    
    private boolean saveAccountPrimary() {
        String[] primaryNames = {
                edtTitle.getText().toString(),
                edtUsername.getText().toString(),
                edtPassword.getText().toString()
        };
    
        if (primaryNames[0].isEmpty()) {
            Toast.makeText(this, "Your title is empty.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (primaryNames[1].isEmpty()) {
            primaryNames[1] = "(no username)";
        }
        if (primaryNames[2].isEmpty()) {
            primaryNames[2] = "(no password)";
        }
        
        
        TempNewAccount.title = primaryNames[0];
        TempNewAccount.username = primaryNames[1];
        TempNewAccount.password = primaryNames[2];
        
        return true;
    }
    
    private void saveAccountSecondary() {
        
        // save group name
        // groupName按理来说应该在showFragment()的时候就获得了
        // 如果没有，就手动获得一次。如下所示
        if (TempNewAccount.groupName.isEmpty()) {
            // TODO TEST
            TempNewAccount.groupName = "Game";
//            TempNewAccount.groupName = "Unclassified";
    
            Logger.d("(19-3836) >> "
                    + "Unclassified or some other groupName >> "
                    + "TempNewAccount.groupName = "
                    + TempNewAccount.groupName);
        }
        
        
        // save note
        TempNewAccount.note = cardMemo.getText().toString();
        
    }
    
    private boolean persistAccount() {
        Realm realm = Realm.getDefaultInstance();
        
        RealmResults<AccountPrimary> primary = realm
                .where(AccountPrimary.class)
                .equalTo("title", TempNewAccount.title)
                .and()
                .equalTo("username", TempNewAccount.username)
                .findAll();
        
        if (primary.size() != 0) {
            // != 0 --> already existed in database
            
            Toast.makeText(this, "Account already exists. ", Toast.LENGTH_SHORT).show();
            
            return false;
            
        } else {
            // == 0 --> not existed in database, so we could create this new account
            
            Account account = new Account(
                    new AccountPrimary(
                            TempNewAccount.title,
                            TempNewAccount.username,
                            TempNewAccount.password
                    ),
                    new AccountSecondary(
                            TempNewAccount.groupName,
                            TempNewAccount.note
                    )
            );
            
            
            // insert new group_item into the current group
            realm.beginTransaction();
            
            realm.insertOrUpdate(account);
            GroupHeader groupHeader = realm
                    .where(GroupHeader.class)
                    .equalTo("groupName", TempNewAccount.groupName)
                    .findFirst();
            
            if (groupHeader != null) {
                groupHeader.getItemList().add(TempNewAccount.generateItem());
                /*Logger.d("(15-1400) >> the new username >> " +
                        "TempNewAccount.groupItem.getAccountTitle() = " +
                        TempNewAccount.groupItem.getAccountTitle());*/
    
                realm.insertOrUpdate(groupHeader);
                
            } /*else {
                // TODO
                // should create a new group header and insert this new item to its
                // RealmList<GroupItem>
    
            }*/
            
            realm.commitTransaction();
//            realm.close();
            
            return true;
        }
        
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // when you clicked photo button
    
    private void openAlbum() {
        // 打开系统图库的 Action，等同于: "android.intent.action.GET_CONTENT"
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // 设置数据类型为图片类型
        intent.setType("image/*");
        startActivityForResult(intent, CHOICE_FROM_ALBUM_REQUEST_CODE);
    }
    
    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output.jpg"));
        startActivityForResult(cropPhotoIntent, CROP_PHOTO_REQUEST_CODE);
    }
    
    private void checkUserPermission() {
        /*
         * 先判断用户以前有没有对我们的应用程序允许过读写内存卡内容的权限，
         * 用户处理的结果在 onRequestPermissionResult 中进行处理
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请读写内存卡内容的权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_SDCARD_PERMISSION_REQUEST_CODE);
        }
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // manage permissions and some receipts
    
    /**
     * 在这里进行用户权限授予结果处理
     *
     * @param requestCode  权限要求码，即我们申请权限时传入的常量
     * @param permissions  保存权限名称的 String 数组，可以同时申请一个以上的权限
     * @param grantResults 每一个申请的权限的用户处理结果数组(是否授权)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            // 调用相机拍照：
            case TAKE_PHOTO_PERMISSION_REQUEST_CODE:
                // 如果用户授予权限，那么打开相机拍照
                if (grantResults.length <= 0 || grantResults[0] != PackageManager
                        .PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT)
                            .show();
                } // else TODO openCamera();
                break;
            // 打开相册选取：
            case WRITE_SDCARD_PERMISSION_REQUEST_CODE:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager
                        .PERMISSION_GRANTED) {
                    Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }
    
    
    /**
     * 通过这个 activity 启动的其他 Activity 返回的结果在这个方法进行处理
     * 我们在这里对拍照、相册选择图片、裁剪图片的返回结果进行处理
     *
     * @param requestCode 返回码，用于确定是哪个 Activity 返回的数据
     * @param resultCode  返回结果，一般如果操作成功返回的是 RESULT_OK
     * @param data        返回对应 activity 返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 通过返回码判断是哪个应用返回的数据
            switch (requestCode) {
                // 拍照
                case TAKE_PHOTO_REQUEST_CODE:
                    cropPhoto(photoUri);
                    break;
                // 相册选择
                case CHOICE_FROM_ALBUM_REQUEST_CODE:
                    cropPhoto(data.getData());
                    break;
                // 裁剪图片
                case CROP_PHOTO_REQUEST_CODE:
                    File file = new File(photoOutputUri.getPath());
                    if (file.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                        btnPhoto.setImageBitmap(bitmap);
//                        file.delete(); // 选取完后删除照片
                    } else {
                        Toast.makeText(this, "Cannot find picture", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        // should close realm instance here...
        // according the suggest of
        // http://mengdd.github.io/Android/Database/Realm/2017/02/27/android-realm-guide/
        // https://www.realm.io/docs/java/latest/#closing-realms
        
    }
}
