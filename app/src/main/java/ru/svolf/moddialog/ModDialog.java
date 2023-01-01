package ru.svolf.moddialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ModDialog {
    private static SharedPreferences mSharedPreferences;
    // mode: dark/light
    private static String textStyle = "DARK";
    // if value == "custom" uses custom font from assets, else system font
    private static String typeface = "CUSTOM";
    
    private static int ID_IMG_BG = 220022;
    private static int ID_IMG_CONT = 221100;
    private static int ID_CONT_CAP = 220011;
    private static int ID_CONT_TEXT = 222211;
    

    
    private static void firstStartDone() {
        mSharedPreferences.edit().putBoolean("Mod.First.Start", false).apply();
    }

    private static boolean isFirstStart() {
        return mSharedPreferences.getBoolean("Mod.First.Start", true);
    }

    public static void prepare(Activity ctx) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        try {
            showCrackerDialog(ctx);
            ModDialog.firstStartDone();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        Log.i("SnowVolfLog", "Done.");
    }

    public static void showCrackerDialog(final Context ctx) throws IOException {
        RelativeLayout mConvertView = initLayout(ctx);
        ImageView mBackground = (ImageView) mConvertView.findViewById(ID_IMG_BG);
        LinearLayout mAvatarContainer = (LinearLayout) mConvertView.findViewById(ID_IMG_CONT);
        TextView mCaption = (TextView) mConvertView.findViewById(ID_CONT_CAP);
        TextView mModderInfo = (TextView) mConvertView.findViewById(ID_CONT_TEXT);
        CircleImageView mAvatar = new CircleImageView(ctx);
        ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(-1, -1);
        mAvatar.setLayoutParams(mParams);
        mAvatarContainer.addView(mAvatar);
        InputStream mBackgroundReader = ctx.getAssets().open("bg.webp");
        InputStream mAvatarReader = ctx.getAssets().open("av.webp");
        Drawable mImg = Drawable.createFromStream(mBackgroundReader, null);
        Drawable mAva = Drawable.createFromStream(mAvatarReader, null);
        mBackground.setImageDrawable(mImg);
        mAvatar.setImageDrawable(mAva);
        mBackgroundReader.close();
        mAvatarReader.close();
        if (textStyle.equalsIgnoreCase("dark")) {
            mCaption.setTextColor(Color.parseColor("#212121"));
        } else if (textStyle.equalsIgnoreCase("light")) {
            mCaption.setTextColor(Color.parseColor("#c8c8c8"));
        }
        if (typeface.equalsIgnoreCase("custom")) {
            Typeface futura = Typeface.createFromAsset(ctx.getAssets(), "relish.ttf");
            mCaption.setTypeface(futura);
            mModderInfo.setTypeface(futura);
        }
        mCaption.setText("Snow Volf");
        mModderInfo.setText(Html.fromHtml(parseText(ctx, "about_cracker.txt")));
        mModderInfo.setMovementMethod(LinkMovementMethod.getInstance());
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setView(mConvertView)
                .setPositiveButton("Ok", null)
                .setNeutralButton("My Profile", null).show();
        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(view -> {
            Uri uri = Uri.parse("http://link.to.profile");
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            ctx.startActivity(Intent.createChooser(intent, "Go to my profile"));
            ModDialog.firstStartDone();
        });
    }

    private static String parseText(Context activity, String url) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(activity.getAssets().open(url), "UTF-8"));
            while (true) {
                String line = br1.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line).append("<br/>");
            }
            br1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static RelativeLayout initLayout(Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;

        RelativeLayout root = new RelativeLayout(context);
        ViewGroup.LayoutParams root_LayoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root_LayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        root_LayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        root.setLayoutParams(root_LayoutParams);

        ImageView imageBackground = new ImageView(context);
        RelativeLayout.LayoutParams imageBackground_LayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageBackground.setId(ID_IMG_BG);
        imageBackground_LayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        imageBackground_LayoutParams.height = (int) (180 * scale + 0.5f);
        imageBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        root.addView(imageBackground);
        imageBackground.setLayoutParams(imageBackground_LayoutParams);

        LinearLayout imageContainer = new LinearLayout(context);
        RelativeLayout.LayoutParams imageContainer_LayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageContainer.setOrientation(LinearLayout.VERTICAL);
        imageContainer.setId(ID_IMG_CONT);
        imageContainer_LayoutParams.width = (int) (100 * scale + 0.5f);
        imageContainer_LayoutParams.height = (int) (100 * scale + 0.5f);
        imageContainer_LayoutParams.topMargin = (int) (125 * scale + 0.5f);
        imageContainer_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        imageContainer_LayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        root.addView(imageContainer);
        imageContainer.setLayoutParams(imageContainer_LayoutParams);

        TextView contentCaption = new TextView(context);
        RelativeLayout.LayoutParams contentCaption_LayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        contentCaption.setTextSize(22f);
        contentCaption.setTypeface(Typeface.DEFAULT_BOLD);
        contentCaption.setId(ID_CONT_CAP);
        contentCaption_LayoutParams.addRule(RelativeLayout.BELOW, ID_IMG_CONT);
        contentCaption_LayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        root.addView(contentCaption);
        contentCaption.setLayoutParams(contentCaption_LayoutParams);

        ScrollView scrollView = new ScrollView(context);
        RelativeLayout.LayoutParams scrollView_LayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        scrollView.setPadding(0, (int) (8 * scale + 0.5f), 0, (int) (8 * scale + 0.5f));
        scrollView_LayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        scrollView_LayoutParams.addRule(RelativeLayout.BELOW, ID_CONT_CAP);
        root.addView(scrollView);
        scrollView.setLayoutParams(scrollView_LayoutParams);

        TextView contentText = new TextView(context);
        contentText.setPadding((int) (16 * scale), (int)(8 * scale), (int) (16 * scale), (int) (8 * scale));
        ScrollView.LayoutParams contentText_LayoutParams =
                new ScrollView.LayoutParams(ScrollView.LayoutParams.WRAP_CONTENT, ScrollView.LayoutParams.WRAP_CONTENT);
        contentText.setTextSize(16f);
        contentText.setId(ID_CONT_TEXT);
        scrollView.addView(contentText);
        contentText.setLayoutParams(contentText_LayoutParams);

        return root;
    }
}