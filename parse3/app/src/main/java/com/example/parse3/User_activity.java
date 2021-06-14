package com.example.parse3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class User_activity extends AppCompatActivity {
    ArrayList<String> userList= new ArrayList<>();
    ListView listView;
    ArrayAdapter arrayAdapter;

    public void getPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.upload_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.upload){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else{
                getPhoto();
            }

        }else if (item.getItemId()==R.id.logout){
            ParseUser.logOut();
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage=data.getData();
        if(resultCode == RESULT_OK && requestCode==1  && data!=null){

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] bytes=byteArrayOutputStream.toByteArray();
                    ParseFile parseFile=new ParseFile("image.png",bytes);
                    ParseObject parseObject=new ParseObject("Images");
                    parseObject.put("image",parseFile);
                    parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Toast.makeText(User_activity.this, "Photo uploaded successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                e.printStackTrace();
                                Toast.makeText(User_activity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




              } catch (IOException e) {
                    e.printStackTrace();
                }


        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getPhoto();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(ParseUser.getCurrentUser().getUsername());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity2);
        listView=findViewById(R.id.listView);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,userList);

        ParseQuery<ParseUser> query= ParseUser.getQuery();

        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
         query.findInBackground(new FindCallback<ParseUser>() {
             @Override
             public void done(List<ParseUser> objects, ParseException e) {
                 if(e==null){
                     if(objects.size()>0){
                         for(ParseUser user: objects){
                             userList.add(user.getUsername());
                         }
                         listView.setAdapter(arrayAdapter);
                     }
                 }else{
                     e.printStackTrace();
                 }
             }
         });
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent=new Intent(getApplicationContext(),ViewFriend.class);
                 intent.putExtra("Friend", userList.get(position));
                 startActivity(intent);
             }
         });


    }
}
