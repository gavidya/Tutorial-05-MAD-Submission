package gavi.sliit.testapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText sID, sName, sAddress, sPhone;
    Button mSaveBtn, mShowBtn, mUpdateBtn, mDeleteBtn;
    DatabaseReference rootRef;

    Student studentID;

    private void clearControls() {
        sID.setText("");
        sName.setText("");
        sAddress.setText("");
        sPhone.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sID = findViewById(R.id.sID);
        sName = findViewById(R.id.sName);
        sAddress = findViewById(R.id.sAddress);
        sPhone = findViewById(R.id.sPhone);

        mSaveBtn = findViewById(R.id.saveBtn);
        mShowBtn = findViewById(R.id.showBtn);
        mUpdateBtn = findViewById(R.id.updateBtn);
        mDeleteBtn = findViewById(R.id.deleteBtn);

        studentID = new Student();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef = FirebaseDatabase.getInstance().getReference().child("Students");

                try {
                    if (TextUtils.isEmpty(sID.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter Student ID", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(sName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter Student Name", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(sAddress.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter Student Address", Toast.LENGTH_SHORT).show();
                    } else {
                        studentID.setID(sID.getText().toString().trim());
                        studentID.setName(sName.getText().toString().trim());
                        studentID.setAddress(sAddress.getText().toString().trim());
                        studentID.setPhone(Integer.parseInt(sPhone.getText().toString().trim()));

                        rootRef.child("St1").setValue(studentID);

                        Toast.makeText(getApplicationContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference showRef = FirebaseDatabase.getInstance().getReference().child("Students").child("St1");
                showRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            sID.setText(dataSnapshot.child("id").getValue().toString());
                            sName.setText(dataSnapshot.child("name").getValue().toString());
                            sAddress.setText(dataSnapshot.child("address").getValue().toString());
                            sPhone.setText(dataSnapshot.child("phone").getValue().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source To Display", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("Students");
                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("St1")) {
                            try {
                                studentID.setID(sID.getText().toString().trim());
                                studentID.setName(sName.getText().toString().trim());
                                studentID.setAddress(sAddress.getText().toString().trim());
                                studentID.setPhone(Integer.parseInt(sPhone.getText().toString().trim()));

                                rootRef = FirebaseDatabase.getInstance().getReference().child("Students").child("St1");
                                rootRef.setValue(studentID);
                                clearControls();

                                Toast.makeText(getApplicationContext(), "Data Successfully Updated", Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source To Display", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference deleteRef = FirebaseDatabase.getInstance().getReference().child("Students");
                deleteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("St1")) {
                            rootRef = FirebaseDatabase.getInstance().getReference().child("Students").child("St1");
                            rootRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "Data Successfully Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source To Delete", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
