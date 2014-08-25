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
PShape logo;

PFont ws_info_txt;


//gui setup
float bg_color = 255;
float ws_bg_color = 200;

// --------------------------------------------- [ przestrzen robocza ]
float ws_width;
float ws_height;

// --------------------------------------------- [ lina ]
float l_len = 31;
float l_int_val = 0.28f;
float l_int_lin = 12;
float l_ofset = 0.12f;
float l_weight = 1;

// --------------------------------------------- [ sin ]
float sin_amp = 20.85f;
int sin_freq = 2;
float sin_bend = 0.5f;

// boolean record_pdf = false;
boolean record_svg = false;
boolean selectPathToExportSVG = false;
String exportPath = "";

// --------------------------------------------- [ offset / zoom ]
float zoom;
PVector offset;
PVector poffset;
PVector mouse;
boolean drag = false;

// --------------------------------------------- [ offset / zoom ]
boolean info_toggle = true;
boolean preset_toggle = false;

String preset_format = "none";

float count = 0;
int seg_nr = 0;
float cal = 0.282222f;


// key code
boolean fA = false;
boolean fB = false;
boolean s0 = false;
boolean s1 = false;
boolean s2 = false;
boolean s3 = false;
boolean s4 = false;
boolean s5 = false;
boolean s6 = false;

public void setup() {

  frameRate(30);
  size(displayWidth, displayHeight);

  logo = loadShape("logo.svg");


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
      .setPosition(ws_menu_x, ws_menu_y)
        .setFont(loadFont("PTSansPro-Regular-12.vlw"))
        .setColorValue(0xff000000);

  cp5.addSlider("ws_width")
    .setCaptionLabel("szeroko\u015b\u0107")
      .setPosition(ws_menu_x, ws_menu_y+ws_menu_s)
        .setSize(ws_menu_w, ws_menu_h)
          .setRange(100, 1000)
            ;

  cp5.addSlider("ws_height")
    .setCaptionLabel("wysoko\u015b\u0107")
      .setPosition(ws_menu_x, ws_menu_y+(ws_menu_s*2))
        .setSize(ws_menu_w, ws_menu_h)
          .setRange(100, 1000)
            ;

  float linia_menu_x = 50;
  float linia_menu_y = 170;
  float linia_menu_s = 20;
  int linia_menu_w = 350;
  int linia_menu_h = 15;

  cp5.addTextlabel("linia")
    .setText("PARAMETRY LINI")
      .setPosition(linia_menu_x, linia_menu_y)
        .setFont(loadFont("PTSansPro-Regular-12.vlw"))
        .setColorValue(0xff000000);

  cp5.addSlider("l_len")
    .setCaptionLabel("d\u0142ugo\u015b\u0107")
      .setPosition(linia_menu_x, linia_menu_y+linia_menu_s)
        .setSize(linia_menu_w, linia_menu_h)
          .setRange(7, 100)
            ;

  cp5.addSlider("l_int_val")
    .setCaptionLabel("odt\u0119p")
      .setPosition(linia_menu_x, linia_menu_y+(linia_menu_s*2))
        .setSize(linia_menu_w, linia_menu_h)
          .setRange(0, 1)
            ;

  cp5.addSlider("l_int_lin")
    .setCaptionLabel("interlinia")
      .setPosition(linia_menu_x, linia_menu_y+(linia_menu_s*3))
        .setSize(linia_menu_w, linia_menu_h)
          .setRange(5, 50)
            ;

  cp5.addSlider("l_ofset")
    .setCaptionLabel("ofset")
      .setPosition(linia_menu_x, linia_menu_y+(linia_menu_s*4))
        .setSize(linia_menu_w, linia_menu_h)
          .setRange(0, 1)
            ;

  cp5.addSlider("l_weight")
    .setCaptionLabel("grubo\u015b\u0107")
      .setPosition(linia_menu_x, linia_menu_y+(linia_menu_s*5))
        .setSize(linia_menu_w, linia_menu_h)
          .setRange(0.5f, 10)
            ;

  float sin_menu_x = 50;
  float sin_menu_y = 300;
  float sin_menu_s = 20;
  int sin_menu_w = 350;
  int sin_menu_h = 15;

  cp5.addTextlabel("sin")
    .setText("WYGI\u0118CIE")
      .setPosition(sin_menu_x, sin_menu_y)
        .setFont(loadFont("PTSansPro-Regular-12.vlw"))
        .setColorValue(0xff000000);

  cp5.addSlider("sin_amp")
    .setCaptionLabel("amplituda")
      .setPosition(sin_menu_x, sin_menu_y+sin_menu_s)
        .setSize(sin_menu_w, sin_menu_h)
          .setRange(0, 100)
            ;

  cp5.addSlider("sin_freq")
    .setCaptionLabel("czestotliwo\u015b\u0107")
      .setPosition(sin_menu_x, sin_menu_y+(sin_menu_s*2))
        .setSize(sin_menu_w, sin_menu_h)
          .setRange(1, 20)
            ;

  cp5.addSlider("sin_bend")
    .setCaptionLabel("wygiecie")
      .setPosition(sin_menu_x, sin_menu_y+(sin_menu_s*3))
        .setSize(sin_menu_w, sin_menu_h)
          .setRange(0, 1)
            ;

  cp5.addButton("export_svg")
    .setLabel("export svg")
      .setPosition( sin_menu_x, sin_menu_y+(sin_menu_s*5) )
        .setSize(350, 20)
          .setColorLabel(0xffffffff);

  // create a toggle
  cp5.addToggle("toggle")
    .setLabel("INFO")
      .setPosition(sin_menu_x, sin_menu_y+(sin_menu_s*7))
        .setSize(50, 20);

  ws_width = 600;
  ws_height = 200;
  // zoom end position offset   


  zoom = 1.0f;
  offset = new PVector((displayWidth)/2, (displayHeight)/2);
  poffset = new PVector(0, 0);
}

public void draw() {

  // 
  float n_l_int_val = ( l_len * l_int_val );
  background(bg_color);

  if( fA == true && s0 == true){
    ws_width = 841 / cal;
    ws_height = 1189 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "A0";
    println("A0");
  }

  if( fA == true && s1 == true){
    ws_width = 594 / cal;
    ws_height = 841 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "A1";
    println("A1");
  }

  if( fA == true && s2 == true){
    ws_width = 420 / cal;
    ws_height = 594 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "A2";
    println("A2");
  }

  if( fA == true && s3 == true){
    ws_width = 297 / cal;
    ws_height = 420 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "A3";
    println("A3");
  }

  if( fA == true && s4 == true){
    ws_width = 210 / cal;
    ws_height = 297 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "A4";
    println("A4");
  }

  if( fA == true && s5 == true){
    ws_width = 148 / cal;
    ws_height = 210 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "A5";
    println("A5");
  }

  if( fA == true && s6 == true){
    ws_width = 105 / cal;
    ws_height = 148 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "A6";
    println("A6"); 
  }

  if( fB == true && s0 == true){
    ws_width = 1000 / cal;
    ws_height = 1414 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "B0";
    println("B0");
  }

  if( fB == true && s1 == true){
    ws_width = 707 / cal;
    ws_height = 1000 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "B1";
    println("B1");
  }

  if( fB == true && s2 == true){
    ws_width = 500 / cal;
    ws_height = 707 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "B2";
    println("B2");
  }

  if( fB == true && s3 == true){
    ws_width = 353 / cal;
    ws_height = 500 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "B3";
    println("B3");
  }

  if( fB == true && s4 == true){
    ws_width = 250 / cal;
    ws_height = 353 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "B4";
    println("B4");
  }

  if( fB == true && s5 == true){
    ws_width = 176 / cal;
    ws_height = 250 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "B5";
    println("B5");
  }

  if( fB == true && s6 == true){
    ws_width = 125 / cal;
    ws_height = 176 / cal;
    count = 0;
    preset_toggle = true;
    preset_format = "B6";
    println("B6"); 
  }

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
    svg = (P8gGraphicsSVG) createGraphics(PApplet.parseInt(ws_width), PApplet.parseInt(ws_height), P8gGraphicsSVG.SVG, exportPath + ".svg");
    beginRecord(svg);
    screen = ((PGraphicsJava2D) g).g2;
    paper = svg.g2;
    paper.setStroke(pen);
  }
  pushMatrix();
  scale(zoom);
  translate(offset.x/zoom, offset.y/zoom);

  float[] _dash = {
    l_len, n_l_int_val
  };

  pen = new BasicStroke(l_weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, _dash, 0);
  Graphics2D g2 = ((PGraphicsJava2D) g).g2;
  g2.setStroke(pen);
  ws_info();

  for (int i = 0; i < ( ws_height / l_int_lin ); i++) {
    qcur(points( i * l_int_lin , false));
    qcur(points( i * l_int_lin + ( l_int_lin/2 ), true));
  }
  popMatrix();

  if (record_svg == true) {
    endRecord();    
    record_svg = false;
    selectPathToExportSVG = false;
  }


  noStroke();
  fill(255,255,255,200);
  rect(0,50,500,450);
  rect(50, 50, 350, 30);
  // shape(logo, 50, 50, 350, 30);

  if(info_toggle == true){
    info();
  }
  shape(logo, 50, 50);

  if(preset_toggle == true){
    counter();
    preset_info(preset_format,counter());
  }
  info_b();
  // info();

}

public void ws_display() {
  stroke(0);
  strokeWeight(0.5f);
  fill(255);
  rect(0, 0, ws_width, ws_height);
  // line_grid(ws_width, ws_height, 50);
}

public void keyPressed() {

  if ( key == 'R') {
    zoom = 1;
    offset.x = 0;
    offset.y = 0;
    // record_svg = true;
  }

  if (key == 'h' || key == 'H' || key == ' ') {
    drag = true;
  } else {
    drag = false;
  }

  if (key == '=') {
    zoom += 0.1f;
    // println("[   zoom   ]"+" + 0.1 ");
  } else if (key == '-') {
    zoom -= 0.1f;
    // println("[   zoom   ]"+" - 0.1 ");
  }

  if (key == 'a') fA = true; 
  if (key == 'b') fB = true;
  if (key == '0') s0 = true;
  if (key == '1') s1 = true;
  if (key == '2') s2 = true;
  if (key == '3') s3 = true;
  if (key == '4') s4 = true;
  if (key == '5') s5 = true;
  if (key == '6') s6 = true;


} 

public void keyReleased() {
  if (key == 'h' || key == 'H' || key == ' ') {
    drag = false;
  }

  if (key == 'a') fA = false; 
  if (key == 'b') fB = false;
  if (key == '0') s0 = false;
  if (key == '1') s1 = false;
  if (key == '2') s2 = false;
  if (key == '3') s3 = false;
  if (key == '4') s4 = false;
  if (key == '5') s5 = false;
  if (key == '6') s6 = false;
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
  } else {
    exportPath = selection.getAbsolutePath();
    selectPathToExportSVG = true;
    println("plik zapisany : " + exportPath + ".svg");
  }
}

public void toggle(boolean theFlag) {
  if(theFlag==true) {
    info_toggle = true;
  } else {
    info_toggle = false;
  }
  println(info_toggle);
}

public float counter(){
  if(count <= 200){
    count++;
  }
  if(count == 200){
    count = 200;
    preset_toggle = false;
  }
  return count;
}





// ------------------------------------------------------------ temp 
public void line_grid() {
  float x = 0;
  float y = 0;
  float step = 50;
  float sep = 50;

  fill(0);
  strokeWeight(0.1f);
  stroke(0);
  // kreski pionowe
  for (int i = 0; i < width/step; i++) {
    x = i * step;
    line(x, 0, x, height);
    text(PApplet.parseInt(x), x, 10);
  }
  // kreski poziome

  for (int i = 0; i < height/step; i++) {
    y = i * step;
    line(0, y, width, y);
    text(PApplet.parseInt(y), 10, y);
  }

  // kursor myszy z koordynatami
  stroke(255);
  text(mouseX + ": x cor", mouseX + 20, mouseY);
  text(mouseY + ": y cor", mouseX + 20, mouseY + 15);
}

public void line_grid(float w_x, float w_y, int sep) {
  noFill();
  strokeWeight(0.1f);
  stroke(0);
  float x = 0;
  float y = 0;
  fill(0);
  for (int i = 1; i < (w_x/sep); i++ ) {
    x = i * sep;
    line(x, 0, x, w_y);
    text(PApplet.parseInt(x), x, 10);
  }

  for (int i = 1; i < (w_y/sep); i++ ) {
    y = i * sep;
    line(0, y, w_x, y);
    text(PApplet.parseInt(y), 10, y);
  }
}

public void temp_info() {
  noStroke();
  pushMatrix();
  translate(width-200, ((height-300)/2));
  fill(0);
  rect(0, 0, 200, 300);
  fill(255);

  text("fps: ", 10, 20);
  text(frameRate, 70, 20);

  text("zoom: ", 10, 40);
  text(zoom, 70, 40);

  text("offset.x: ", 10, 60);
  text(offset.x, 70, 60);

  text("offset.y: ", 10, 80);
  text(offset.y, 70, 80);

  text("poffset.x: ", 10, 100);
  text(poffset.x, 70, 100);

  text("poffset.y", 10, 120);
  text(poffset.y, 70, 120);

  popMatrix();
}
PFont font_a, font_b,font_c;
public void info(){
	font_a = loadFont("PTSansPro-Regular-12.vlw");
	font_b = loadFont("PTSansPro-Regular-12.vlw");
	fill(255);
	strokeWeight(1);
	stroke(0);
	rect(0,50,950,430);

	noStroke();

	fill(0);
	pushMatrix();	
	translate(410,50);
	// rect(0,0,50,50);
	textFont(font_a);
	// text("   INFO ",20,60);
	textFont(font_b);
	text(" - obszaru roboczego i obszaru exportu ",43,80);
	text(" - obszaru roboczego i obszaru exportu ",41,100);

	text(" - pojedy\u0144czej lini ",33,150);
	text(" - pomi\u0119dazy liniami ",22,170);
	text(" - pomiedzy wierszami ",40,190);
	text(" - co drugiej lini ",20,210);
	text(" - loini ",33,230);

	text(" - wygiecia ",40,280);
	text(" - powt\u00f3rze\u0144 wygiecia ",63,300);
	text(" - ??? ",34,320);

	popMatrix();

	pushMatrix();	
	translate(680,50);
	textFont(font_a);
	text(" SHORTCUT ",20,60);
	
	textFont(font_b);
	text(" przesuwanie",20,80);
	text(" [ space ] + [ LMB ]  lub  [ h ] + [ LMB ]",20,100);
	// text(" [ h ] + [ LMB ] ",20,120);

	// textFont(font_a);
	text(" powi\u0119kszanie | zoom in",20,140);
	textFont(font_b);
	text(" [ Ctrl ] + [ + ]  lub  [ scrol ]",20,160);
	// text(" [ scrol ] ",20,180);

	// textFont(font_a);
	text(" pomniejszanie | zoom out ",20,200);
	textFont(font_b);
	text(" [ Ctrl ] + [ - ]  lub  [ scrol ]",20,220);
	// text(" [ scrol ] ",20,240);

	// textFont(font_a);
	text(" presety format\u00f3w ",20,260);
	textFont(font_b);
	text(" [ A ] + [ 0 - 6 ]  i  [ B ] + [ 0 - 6 ]",20,280);

	// textFont(font_a);
	text(" viewport reset ",20,320);
	textFont(font_b);
	text(" [ Shift ] + [ r ] ",20,340);
	// text(" [ b ] + [ 0 - 6 ] ",20,340);

	text(" * [ LMB ] lewy przycisk myszy ",20,380);

	popMatrix();
}


public void ws_info(){


  ws_info_txt = createFont("PT Sans Pro",12);
  textFont(ws_info_txt);
  textAlign(LEFT);
  
  fill(0);
  pushMatrix();
  translate(ws_width,-10);
  text(" szeroko\u015b\u0107 ",10,20);
  text(ws_width,10,40);
  text(" px ", 70,40);
  text(ws_width * cal,10,50);
  text(" mm ", 70,50);

  text(" wysoko\u015b\u0107 ",10,80);
  text(ws_height,10,100);
  text(" px ", 70,100);
  text(ws_height * cal,10,110);
  text(" mm ", 70,110);

  popMatrix();
  noFill();
}

public void info_b(){
	fill(0);
	font_c = loadFont("PTSansPro-Regular-12.vlw");
	textFont(font_c);
	textAlign(RIGHT);
	text(" fps : " + PApplet.parseInt(frameRate) + " | " + " zoom : " + PApplet.parseInt(zoom * 100) + " %",width-10,20);
}

public void preset_info(String str, float _con){
  PFont preset_info_txt = loadFont("PTSansPro-Bold-62.vlw");
  textFont(preset_info_txt);
  textAlign(CENTER);
  noStroke();

	fill(255,map(_con,1,50,255,10));
  pushMatrix();
  translate((width-350)/2,50);
  rect(0,0,350,60);
  popMatrix();

  fill(0,map(_con,1,50,255,10));
  text("FORMAT " + str,width/2,100);
}

// funkcja usrala koordynaty puknkt\u00f3w potrzebne 
public float[][] points(float _y,boolean seg_nr){
	float x , y;
	float nr = (sin_freq*6);

	float sep = ( ws_width / (nr));

	float[][]ppos = new float[PApplet.parseInt(nr+1)][PApplet.parseInt(nr+1)];
	float[][]new_ppos = new float[PApplet.parseInt(nr+1)][PApplet.parseInt(nr+1)];


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

	float[][] to_fix = new float[2][4];
	float[][] fix = new float[2][4];

	for(int i =0; i < 4; i++){
		to_fix[0][i] = ppos[0][i];
		to_fix[1][i] = ppos[1][i];
	}

	float[][] dupa = sub_seg(to_fix,l_ofset);


	for(int i = 0; i < ppos.length; i++){
		new_ppos[0][i] = ppos[0][i];
		new_ppos[1][i] = ppos[1][i];
	}

	if(seg_nr == true){
		for(int i = 0; i < 4; i++){
			new_ppos[0][i] = dupa[0][i];
			new_ppos[1][i] = dupa[1][i];
		}
	}


	return new_ppos;
}

public void qcur(float[][] _pos){

	int seg_nr = 0;

	// text(_pos.length,50,50);
	noFill();
	stroke(0);
	// strokeWeight(1);

	beginShape();
	for(int i = 0; i < _pos.length-6; i+=6){
		vertex(_pos[0][i],_pos[1][i]);
		bezierVertex(_pos[0][i+1],_pos[1][i+1],_pos[0][i+2],_pos[1][i+2],_pos[0][i+3],_pos[1][i+3]);
		bezierVertex(_pos[0][i+4],_pos[1][i+4],_pos[0][i+5],_pos[1][i+5],_pos[0][i+6],_pos[1][i+6]);
		seg_nr = seg_nr + 1;
	}
	endShape();
	// println(seg_nr);
}

public float[][] sub_seg(float[][] _in, float ppos){

	stroke(0);
	// float ppos = map(mouseX,0,width,0,1);
	float[][] p = new float [2][6];
	float[][] out = new float [2][4];
	for(int i = 0; i < 3; i++){
		p[0][i] = sub( _in[0][i], _in[0][i+1], ppos);
		p[1][i] = sub( _in[1][i], _in[1][i+1], ppos);
	}

	for(int i = 0; i < 2; i++){
		p[0][i+3] = sub( p[0][i], p[0][i+1], ppos);
		p[1][i+3] = sub( p[1][i], p[1][i+1], ppos);		
	}

	p[0][5] = sub( p[0][3], p[0][4], ppos);
	p[1][5] = sub( p[1][3], p[1][4], ppos);		

	out[0][0] = p[0][5];
	out[1][0] =	p[1][5];
	
	out[0][1] = p[0][4];
	out[1][1] = p[1][4];
	
	out[0][2] = p[0][2];
	out[1][2] = p[1][2];
	
	out[0][3] =	_in[0][3]; 
	out[1][3] =	_in[1][3];

	// println("out" + out[0][1]);
	
	return out;

}

public float sub(float _in_a, float _in_b, float _pos){
	float out =	map(_pos,0,1,_in_a,_in_b);

	return out;

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
