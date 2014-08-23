import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.philhosoft.p8g.svg.P8gGraphicsSVG; 
import controlP5.*; 
import java.awt.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ck_raster extends PApplet {

// m_ratser - ck raster generator //
// generator rastra //






P8gGraphicsSVG svg;
ControlP5 cp5;

Graphics2D  screen;
Graphics2D  paper;
BasicStroke pen;


//gui setup
float bg_color = 255;
float ws_bg_color = 200;

// --------------------------------------------- [ przestrzen robocza ]
float ws_width = 500;
float ws_height = 200;

// --------------------------------------------- [ lina ]
float l_len = 1;
float l_int_val = 1;
float l_int_lin = 10;
float l_ofset = 1;
float l_weight = 1;

// --------------------------------------------- [ sin ]
int sin_freq = 2;
float sin_amp = 100;
float sin_bend = 1;

// boolean record_pdf = false;
boolean record_svg = false;
boolean selectPathToExportSVG = false;
String exportPath = "";

float zoom;
PVector offset;
PVector poffset;
PVector mouse;
boolean drag = false;

public void setup() {
	frameRate(30);
	size(displayWidth, displayHeight);

	smooth();
  noStroke();  
  cp5 = new ControlP5(this);
	PFont p = loadFont("PTSansPro-Regular-9.vlw");

	cp5.setControlFont(p);
  cp5.setColorLabel(0xff000000);
  cp5.setColorForeground(0xff000000);
  cp5.setColorBackground(0xffB4B4B4);
  cp5.setColorActive(0xff838383);

  // --------------------------------------------- [ przestrzen robocza ]

	float ws_menu_x = 50;
	float ws_menu_y = 100;
	float ws_menu_s = 20;
	int ws_menu_w = 350;
	int ws_menu_h = 15;

	cp5.addTextlabel("workspace")
		.setText("PRZESTRZE\u0143 ROBOCZA")
		.setPosition(ws_menu_x,ws_menu_y)
		// .setFont(loadFont("PTSansPro-Regular-12.vlw"))
		.setColorValue(0xff000000);

	cp5.addSlider("ws_width")
		.setCaptionLabel("szeroko\u015b\u0107")
		.setPosition(ws_menu_x,ws_menu_y+ws_menu_s)
		.setSize(ws_menu_w,ws_menu_h)
		.setRange(100,1000)
    ;

	cp5.addSlider("ws_height")
		.setCaptionLabel("wysoko\u015b\u0107")
		.setPosition(ws_menu_x,ws_menu_y+(ws_menu_s*2))
		.setSize(ws_menu_w,ws_menu_h)
		.setRange(100,1000)
    ;

	float linia_menu_x = 50;
	float linia_menu_y = 200;
	float linia_menu_s = 20;
	int linia_menu_w = 350;
	int linia_menu_h = 15;

	cp5.addTextlabel("linia")
		.setText("PARAMETRY LINI")
		.setPosition(linia_menu_x,linia_menu_y)
		// .setFont(loadFont("PTSansPro-Regular-12.vlw"))
		.setColorValue(0xff000000);

	cp5.addSlider("l_len")
		.setCaptionLabel("d\u0142ugo\u015b\u0107")
		.setPosition(linia_menu_x,linia_menu_y+linia_menu_s)
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(1,100)
    ;

	cp5.addSlider("l_int_val")
		.setCaptionLabel("odt\u0119p")
		.setPosition(linia_menu_x,linia_menu_y+(linia_menu_s*2))
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(0,1)
    ;

	cp5.addSlider("l_int_lin")
		.setCaptionLabel("interlinia")
		.setPosition(linia_menu_x,linia_menu_y+(linia_menu_s*3))
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(5,50)
    ;
	
	cp5.addSlider("l_ofset")
		.setCaptionLabel("ofset")
		.setPosition(linia_menu_x,linia_menu_y+(linia_menu_s*4))
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(100,1000)
    ;

	cp5.addSlider("l_weight")
		.setCaptionLabel("grubo\u015b\u0107")
		.setPosition(linia_menu_x,linia_menu_y+(linia_menu_s*5))
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(0.1f,5)
    ;

	float sin_menu_x = 50;
	float sin_menu_y = 400;
	float sin_menu_s = 20;
	int sin_menu_w = 350;
	int sin_menu_h = 15;

	cp5.addTextlabel("sin")
		.setText("sinus")
		.setPosition(sin_menu_x,sin_menu_y)
		// .setFont(loadFont("PTSansPro-Regular-12.vlw"))
		.setColorValue(0xff000000);

	cp5.addSlider("sin_amp")
		.setCaptionLabel("amplituda")
		.setPosition(sin_menu_x,sin_menu_y+sin_menu_s)
		.setSize(sin_menu_w,sin_menu_h)
		.setRange(-100,100)
    ;

	cp5.addSlider("sin_freq")
		.setCaptionLabel("czestotliwo\u015b\u0107")
		.setPosition(sin_menu_x,sin_menu_y+(sin_menu_s*2))
		.setSize(sin_menu_w,sin_menu_h)
		.setRange(1,10)
    ;

	cp5.addSlider("sin_bend")
		.setCaptionLabel("wygiecie")
		.setPosition(sin_menu_x,sin_menu_y+(sin_menu_s*3))
		.setSize(sin_menu_w,sin_menu_h)
		.setRange(0,1)
    ;

	cp5.addButton("export_svg")
	   .setLabel("export svg")
	   .setPosition( 50, 500 )
	   .setSize(350, 20)
	   .setColorLabel(0xffffffff);
	// zoom end position offset   

  zoom = 1.0f;
  offset = new PVector((displayWidth)/2, (displayHeight)/2);
  poffset = new PVector(0, 0);
}
 
public void draw() {

	float n_l_int_val = ( l_len * l_int_val );



	background(bg_color);
	line_grid();

	pushMatrix();
	scale(zoom);
	translate(offset.x/zoom, offset.y/zoom);
	ws_display();
	popMatrix();

	if (selectPathToExportSVG == true) {
    println("selectPathToExportSVG = " + selectPathToExportSVG);
    record_svg = true;
    zoom = 1.0f;
    offset.x = 0.0f - (0);
    offset.y = 0.0f - (0);
    record_svg = true;
  }

	if (record_svg) {
    svg = (P8gGraphicsSVG) createGraphics(PApplet.parseInt(ws_width), PApplet.parseInt(ws_height), P8gGraphicsSVG.SVG,exportPath + ".svg");
		beginRecord(svg);
		screen = ((PGraphicsJava2D) g).g2;
    paper = svg.g2;
    paper.setStroke(pen);
	}
	pushMatrix();
	scale(zoom);
	translate(offset.x/zoom, offset.y/zoom);

	float[] _dash = {l_len,n_l_int_val};

	pen = new BasicStroke(l_weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, _dash, 0);
  Graphics2D g2 = ((PGraphicsJava2D) g).g2;
  g2.setStroke(pen);
	
	for(int i = 0 ; i < ( ws_height / l_int_lin ); i++){
		qcur(points(i*l_int_lin));
	}
	popMatrix();

  if(record_svg == true) {
    endRecord();    
    record_svg = false;
    selectPathToExportSVG = false;
  }



	temp_info();
	noFill();
	strokeWeight(1);
	stroke(255,0,0);
	ellipse(offset.x, offset.y, 15, 15);
	ellipse(poffset.x, poffset.y, 15, 15);
	line(offset.x, offset.y,poffset.x, poffset.y);
	fill(255,0,0);
	text("offset",offset.x, offset.y-20);
	text("poffset",poffset.x, poffset.y-20);


//	temp
	

}

public void ws_display(){
	fill(ws_bg_color);
	rect(0,0,ws_width,ws_height);
	line_grid(ws_width,ws_height,50);
}

public void keyPressed(){

	if ( key == 'R'){
		zoom = 1;
		offset.x = 0;
		offset.y = 0;
    // record_svg = true;
	}

	if (key == 'h' || key == 'H' || key == ' ') {
		drag = true;
	}
	else {
		drag = false;
	}

	if (key == '=') {
		zoom += 0.1f;
	// println("[   zoom   ]"+" + 0.1 ");
	} 
	else if (key == '-') {
		zoom -= 0.1f;
	// println("[   zoom   ]"+" - 0.1 ");
	}
} 

public void keyReleased() {
  if (key == 'h' || key == 'H' || key == ' ') {
    drag = false;
  }
}

// Store the mouse and the previous offset
public void mousePressed() {
  if ( drag == true) {
    mouse = new PVector(mouseX, mouseY);
    poffset.set(offset);
  }
}

// Calculate the new offset based on change in mouse vs. previous offsey
public void mouseDragged() {
  if ( drag == true) {
    offset.x = mouseX - mouse.x + poffset.x;
    offset.y = mouseY - mouse.y + poffset.y;
  }
}

public void mouseWheel(MouseEvent event) {
  float e = event.getAmount();
  if ( e == -1.0f) {
    zoom += 0.1f;
  }
  if (e == 1.0f) {
    zoom -= 0.1f;
  }
  if (zoom <= 0.1f) {
    zoom = 0.1f;
  }
}

public void export_svg() {
  selectOutput("wybie\u017c lokalizacje:", "exportFileSVG");
  // println();
  selectPathToExportSVG = true;
}

public void exportFileSVG(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  }

  else {
      exportPath = selection.getAbsolutePath();
      selectPathToExportSVG = true;
    println("plik zapisany : " + exportPath + ".svg");
  }
}


// ------------------------------------------------------------ temp 
public void line_grid(){
	float x = 0;
	float y = 0;
	float step = 100;
	float sep = 100;

	fill(0);
	strokeWeight(0.1f);
	stroke(0);
	// kreski pionowe
	for(int i = 0; i < width/step; i++){
		x = i * step;
		line(x,0,x,height);
		text(PApplet.parseInt(x),x,10);
	}
	// kreski poziome

	for(int i = 0; i < height/step; i++){
		y = i * step;
		line(0,y,width,y);
		text(PApplet.parseInt(y),10,y);
	}

	// kursor myszy z koordynatami
	stroke(255);
	text(mouseX + ": x cor", mouseX + 20, mouseY);
	text(mouseY + ": y cor", mouseX + 20, mouseY + 15);



}

public void line_grid(float w_x, float w_y, int sep)	{
	noFill();
	strokeWeight(0.1f);
	stroke(0);
	float x = 0;
	float y = 0;
	fill(0);
	for(int i = 1; i < (w_x/sep); i++ ){
		x = i * sep;
		line(x,0,x,w_y);
		text(PApplet.parseInt(x),x,10);
	}

	for(int i = 1; i < (w_y/sep); i++ ){
		y = i * sep;
		line(0,y,w_x,y);
		text(PApplet.parseInt(y),10,y);
	}

}

public void temp_info() {
	noStroke();
	pushMatrix();
	translate(width-200, ((height-300)/2));
	fill(0);
	rect(0,0,200,300);
	fill(255);

	text("fps: ",10,20);
	text(frameRate,70,20);

	text("zoom: ",10,40);
	text(zoom,70,40);

	text("offset.x: ",10,60);
	text(offset.x,70,60);

	text("offset.y: ",10,80);
	text(offset.y,70,80);

	text("poffset.x: ",10,100);
	text(poffset.x,70,100);

	text("poffset.y",10,120);
	text(poffset.y,70,120);

	popMatrix();
}

// funkcja usrala koordynaty puknkt\u00f3w potrzebne 
public float[][] points(float _y){
	float x , y;
	float nr = (sin_freq*6);

	float sep = ( ws_width / (nr));

	float[][]ppos = new float[PApplet.parseInt(nr+1)][PApplet.parseInt(nr+1)];

	for(int i = 0; i < nr+1; i++){
		x = i * sep;
		y = _y;
		ppos[0][i] = x;
		ppos[1][i] = y;
	}

	for(int i = 1; i < ppos.length; i+=6){
		ppos[0][i] = ppos[0][i-1] + (( sep*2 ) * sin_bend);
		ppos[1][i] = ppos[1][i] + sin_amp;
	}
	for(int i = 2; i < ppos.length; i+=6){
		ppos[0][i] = ppos[0][i+1] - (( sep*2 ) * sin_bend);
		ppos[1][i] = ppos[1][i] + sin_amp;
	}
	for(int i = 4; i < ppos.length; i+=6){
		ppos[0][i] = ppos[0][i-1] + (( sep*2 ) * sin_bend);
		ppos[1][i] = ppos[1][i] + ( sin_amp * -1 );
	}
	for(int i = 5; i < ppos.length; i+=6){
		ppos[0][i] = ppos[0][i+1] - (( sep*2 ) * sin_bend);
		ppos[1][i] = ppos[1][i] + ( sin_amp * -1 );
	}

	// for(int i = 0; i < ppos.length; i++){
	// 	ellipse(ppos[0][i],ppos[1][i], 5, 5);
	// 	text(i,ppos[0][i],ppos[1][i]);
	// }
	// sswraca dwuwymiarowy array
	return ppos;
}

public void qcur(float[][] _pos){

	// text(_pos.length,50,50);
	noFill();
	stroke(0);
	// strokeWeight(1);

	beginShape();
	for(int i = 0; i < _pos.length-6; i+=6){
		vertex(_pos[0][i],_pos[1][i]);
		bezierVertex(_pos[0][i+1],_pos[1][i+1],_pos[0][i+2],_pos[1][i+2],_pos[0][i+3],_pos[1][i+3]);
		bezierVertex(_pos[0][i+4],_pos[1][i+4],_pos[0][i+5],_pos[1][i+5],_pos[0][i+6],_pos[1][i+6]);
	}
	endShape();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ck_raster" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
