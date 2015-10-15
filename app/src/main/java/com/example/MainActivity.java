package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

public class MainActivity extends Activity {

	private Button createPDF , openPDF;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createPDF = (Button)findViewById(R.id.button1);
		createPDF.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createPDF();
			}
		});
		
		openPDF = (Button)findViewById(R.id.button2);
		openPDF.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openPdf();
			}
		});
	}

	public void createPDF()
	{
		Document doc = new Document();

		try {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

			File dir = new File(path);
			if(!dir.exists())
				dir.mkdirs();

			Log.d("PDFCreator", "PDF Path: " + path);

			File file = new File(dir, "demo.pdf");
			FileOutputStream fOut = new FileOutputStream(file);

			PdfWriter.getInstance(doc, fOut);

			//open the document
			doc.open();

			/* Create Paragraph and Set Font */
			Paragraph p1 = new Paragraph("Hi! I am Generating my first PDF using DroidText");
			/* Create Set Font and its Size */
			Font paraFont= new Font(Font.HELVETICA);
			paraFont.setSize(16);
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p1.setFont(paraFont);

			//add paragraph to document    
			doc.add(p1);

			
			Paragraph p2 = new Paragraph("This is an example of a simple paragraph");
			/* You can also SET FONT and SIZE like this */
			Font paraFont2= new Font(Font.COURIER,14.0f,Color.GREEN);
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setFont(paraFont2);

			doc.add(p2);

			/* Inserting Image in PDF */
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.android);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
			Image myImg = Image.getInstance(stream.toByteArray());
			myImg.setAlignment(Image.MIDDLE);

			//add image to document
			doc.add(myImg);

			//set footer
			Phrase footerText = new Phrase("This is an example of a footer");
			HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
			doc.setFooter(pdfFooter);

			Toast.makeText(getApplicationContext(), "Created...", Toast.LENGTH_LONG).show();
			
		} catch (DocumentException de) {
			Log.e("PDFCreator", "DocumentException:" + de);
		} catch (IOException e) {
			Log.e("PDFCreator", "ioException:" + e);
		} 
		finally
		{
			doc.close();
		}
	}      
	
	void openPdf()
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

		File file = new File(path, "demo.pdf");
		
		intent.setDataAndType( Uri.fromFile( file ), "application/pdf" );
	    startActivity(intent);
	}
}