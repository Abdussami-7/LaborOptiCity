package com.example.laboropticity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class AcceptRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);
        String reqId = getIntent().getStringExtra("CURRENT_REQ_ID");
        Toast.makeText(this, "req id: "+reqId, Toast.LENGTH_SHORT).show();
        ImageView qrImgView = findViewById(R.id.QR_ImgView);
        Bitmap qrBitmap = generateQRCode(reqId);
        qrImgView.setImageBitmap(qrBitmap);
    }

    private Bitmap generateQRCode(String data){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        }
        catch (WriterException e){
            e.printStackTrace();
            return null;
        }
    }
}