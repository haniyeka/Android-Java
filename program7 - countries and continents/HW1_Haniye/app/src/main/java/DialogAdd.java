import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_haniye.R;

public class DialogAdd extends AppCompatActivity {

    final Context context = this;
    private Button button;
    public int countinent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        Button button = (Button) findViewById(R.id.add_country);
        final TextView usernameText = (TextView) findViewById(R.id.new_country);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

    }
}
