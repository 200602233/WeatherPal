package com.example.weatherpal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);



        // code from week 4 Intent Slide 14
        //� Use try/catch as an alternative to resolveActivity() on Android 11+ (API 30+).
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.putExtra(Intent.EXTRA_EMAIL,
//                new String[]{"support@weathernow.app"});
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
//                "WeatherNow — Feedback");
//// Always check a handler exists before calling startActivity
//        if (emailIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(emailIntent);
//        } else {
//            Toast.makeText(this,
//                    "No email app found",
//                    Toast.LENGTH_SHORT).show();
//        }

        // code from week 4-Intent slide 15
        /*
            Assignment Tip
Link to your own GitHub repo. The URL can be anything
valid — the marker checks the intent fires.
         */
        // Open a URL in the default browser
//        String url = "https://github.com/yourusername/weathernow";
//        Intent browserIntent = new Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse(url)
//        );
//// Guard against no browser being installed
//        try {
//            startActivity(browserIntent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(
//                    this,
//                    "No browser app found",
//                    Toast.LENGTH_SHORT
//            ).show();
//        }


        // code from week 4-Intent slide 16
        // Build a sharing Intent for plain text
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain"); // MIME type
//        shareIntent.putExtra(
//                Intent.EXTRA_TEXT,
//                "Check out WeatherNow! 🌤 https://example.com"
//        );
//// Wrap in a chooser — user picks the app
//        Intent chooser = Intent.createChooser(
//                shareIntent,
//                "Share WeatherNow via..."
//        );
        //// Guard: show Toast if nothing handles ACTION_SEND
//        if (shareIntent.resolveActivity(
//                getPackageManager()) != null) {
//            startActivity(chooser);
//        }
    }
}