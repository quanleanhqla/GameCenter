package com.example.quanla.quannet.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.Comments;
import com.example.quanla.quannet.database.models.GameRoom;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {


    private DatabaseReference mDatabase;
    @BindView(R.id.im_fb)
    ImageButton imfb;
    @BindView(R.id.im_gg)
    ImageButton imgg;
    @BindView(R.id.loginButton)
    LoginButton loginButton;
    @BindView(R.id.next)
    Button next;

    private static final String TAG = "AccountActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupUI();

        mDatabase = FirebaseDatabase.getInstance().getReference();


//        mDatabase.child("hot").child("Playdota Stadium").setValue(new GameRoom(null, "Playdota Stadium", "79 Đặng Văn Ngữ, Trung Tự, Đống Đa, Hà Nội", "Excellent", 21.009882,105.8290213));
//        mDatabase.child("hot").child("Cybox Game Center").setValue(new GameRoom(null, "Cybox Game Center", "52 Nguyễn Hữu Huân, Lý Thái Tổ, Hoàn Kiếm, Hà Nội", "Excellent", 21.0334396,105.8522325));
//        mDatabase.child("hot").child("Vikings Gaming").setValue(new GameRoom(null, "Vikings Gaming", "Số 8 Ngõ 198B, Nguyễn Tuân, Thanh Xuân, Hanoi", "Excellent", 21.0015355,105.7993634));
//        mDatabase.child("hot").child("Pegasus Club Center").setValue(new GameRoom(null, "Pegasus Club Center", "số 20 ngõ 165 Cầu Giấy – Hà Nội", "Excellent", 21.0321938,105.7951977));
//        mDatabase.child("hot").child("GameHome").setValue(new GameRoom(null, "GameHome", "123 Nguyễn Ngọc Vũ, Trung Hoà, Cầu Giấy, Hà Nội", "Excellent", 21.0103867,105.8079826));
//        mDatabase.child("hot").child("Gaming House").setValue(new GameRoom(null, "Gaming House", "Tầng 3, 169 Yên Hòa, Yên Hoà, Cầu Giấy, Hà Nội", "Excellent", 21.0277271,105.7906703));
//        mDatabase.child("hot").child("Imba eSports Stadium").setValue(new GameRoom(null, "GImba eSports Stadium", "152 Phó Đức Chính, Trúc Bạch, Ba Đình, Hà Nội", "Excellent", 21.045606,105.8410282));
//        mDatabase.child("hot").child("Monaco Game").setValue(new GameRoom(null, "Monaco Game", "tầng 6, 22 Thành Công, Khu tập thể Bắc Thành Công, Ba Đình, Hà Nội", "Excellent", 21.0221663,105.8136659));
//        mDatabase.child("hot").child("Colosseum Gaming Center").setValue(new GameRoom(null, "Colosseum Gaming Center", "Tầng 2, Khu B Chợ Hôm – Đức Viên, 72 Trân Xuân Soạn, Quận Hai Bà Trưng Hà nội", "Excellent", 21.0168659,105.8499187));
//        mDatabase.child("hot").child("Only One Airport Gaming").setValue(new GameRoom(null, "Only One Airport Gaming", "12-14 ngõ 295 Bạch Mai, Hai Bà Trưng, Hà Nội", "Excellent", 21.0056797,105.849649));
//        mDatabase.child("hot").child("Epic Gaming Center").setValue(new GameRoom(null, "Epic Gaming Center", "Phố Huế Tầng 3 Chợ Hôm, 79 Huế, Ngô Thị Nhậm, Hai Bà Trưng, Hà Nội", "Excellent", 21.017472, 105.851606));
//        mDatabase.child("hot").child("Game Vip").setValue(new GameRoom(null, "Game Vip", "69 Phó Đức Chính, Quận Ba Đình, Hà Nội", "Excellent", 21.0483626,105.839595));
//        mDatabase.child("hot").child("G5 E-Sport Center").setValue(new GameRoom(null, "G5 E-Sport Center", "38 Trần Đại Nghĩa, Đồng Tâm, Hai Bà Trưng, Hà Nội", "Excellent", 20.995902, 105.845164));
//        mDatabase.child("hot").child("Moon Game").setValue(new GameRoom(null, "Moon Game", "7B/165, Xuân Thủy, Quận Cầu Giấy, Hà Nội", "Excellent", 21.0484441,105.8067639));
//        mDatabase.child("hot").child("Nhiệt Game").setValue(new GameRoom(null, "Nhiệt Game", "501 Vũ Tông Phan - Khương Đình - Hà Nội", "Excellent", 20.9857635,105.8122045));
//        mDatabase.child("hot").child("Royal Gaming").setValue(new GameRoom(null, "Royal Gaming", "03 Nam Đồng, Đống Đa, Hà Nội", "Excellent", 21.0134829,105.831058));
//        mDatabase.child("hot").child("Arena Gaming Center").setValue(new GameRoom(null, "Arena Gaming Center", "Tầng 5, 52 Chùa Hà, Hà Nội", "Excellent", 21.035984,105.7928853));
//        mDatabase.child("hot").child("Cyzone").setValue(new GameRoom(null, "Cyzone", "79 Đặng Văn Ngữ, Đống Đa, Hà Nội", "Excellent", 21.010281,105.8288293));
//        mDatabase.child("hot").child("H3 Cyber Gaming").setValue(new GameRoom(null, "H3 Cyber Gaming", "155 Vũ Tông Phan -Khương Đình, Hà Nội", "Excellent", 20.9978033,105.8147823));
//        mDatabase.child("hot").child("Clan 105").setValue(new GameRoom(null, "Clan 105", "105 đường Nguyễn Đức Cảnh, quận Hoàng Mai, Hà Nội", "Excellent", 20.9874936,105.8478256));

    }

    private void setupUI() {
        ButterKnife.bind(this);
        if (FirebaseAuth.getInstance().getCurrentUser()!= null)
            replaceActivity();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity();
            }
        });
        mCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...

            }
        };
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        imgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: 1");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            Log.d(TAG, String.format("onActivityResult: %s", result.isSuccess()));

            if (result.isSuccess()) {
                Log.d(TAG, "onActivityResult: 2");
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.d(TAG, "onActivityResult: 3");
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        replaceActivity();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(AccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        replaceActivity();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(AccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    @Override
    protected void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mAuth.removeAuthStateListener(mAuthListener);
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {

    }
    public void replaceActivity(){
        Intent intent = new Intent(this,CoreActivity.class);
        startActivity(intent);
    }
}