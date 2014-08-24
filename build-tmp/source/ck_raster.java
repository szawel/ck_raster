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

float zoom;
PVector offset;
PVector poffset;
PVector mouse;
boolean drag = false;

//temp
boolean info_swith = false;

int seg_nr = 0;

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

  // rect(50, 50, 350, 30);

  // linie i coordynaty dla background
  // line_grid();


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

  if(info_swith == true){
    info();
  }
  shape(logo, 50, 50);
  // info();

}

public void ws_display() {
  stroke(0);
  strokeWeight(0.5f);
  fill(255);
  rect(0, 0, ws_width, ws_height);
  // line_grid(ws_width, ws_height, 50);
}

public void ws_info(){
  float cal = 0.282222f;
  
  fill(0);
  pushMatrix();
  translate(ws_width,-10);
  text(" szeroko\u015b\u0107 ",10,20);
  text(ws_width,10,40);
  text(" px ", 70,40);
  text(ws_width * cal,10,60);
  text(" mm ", 70,60);

  text(" wysoko\u015b\u0107 ",10,80);
  text(ws_height,10,100);
  text(" px ", 70,100);
  text(ws_height * cal,10,120);
  text(" mm ", 70,120);

  popMatrix();
  noFill();
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
  } else {
    exportPath = selection.getAbsolutePath();
    selectPathToExportSVG = true;
    println("plik zapisany : " + exportPath + ".svg");
  }
}

public void toggle(boolean theFlag) {
  if(theFlag==true) {
    info_swith = true;
  } else {
    info_swith = false;
  }
  println(info_swith);
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
PFont font_a, font_b;
public void info(){
	font_a = loadFont("PTSansPro-Regular-12.vlw");
	font_b = loadFont("PTSansPro-Italic-12.vlw");
	fill(255);
	strokeWeight(1);
	stroke(0);
	rect(0,50,900,430);

	noStroke();

	fill(0);
	pushMatrix();	
	translate(455,50);
	// rect(0,0,50,50);
	textFont(font_a);
	text("   INFO ",20,60);
	textFont(font_b);
	text(" - obszaru roboczego i obszaru exportu ",20,80);
	text(" - obszaru roboczego i obszaru exportu ",20,100);

	text(" - pojedy\u0144czej lini ",20,150);
	text(" - pomi\u0119dazy liniami ",20,170);
	text(" - pomiedzy wierszami ",20,190);
	text(" - co drugiej lini ",20,210);
	text(" - loini ",20,230);

	text(" - wygiecia ",20,280);
	text(" - powt\u00f3rze\u0144 wygiecia ",20,300);
	text(" - ??? ",20,320);

	popMatrix();

	pushMatrix();	
	translate(700,50);
	textFont(font_a);
	text(" SHORTCUT ",20,60);
	
	text(" PRZESUWANIE",20,80);
	textFont(font_b);
	text(" [ space ] + [ LMB ] ",20,100);
	text(" [ h ] + [ LMB ] ",20,120);

	textFont(font_a);
	text(" POWIEKSZENIE ",20,140);
	textFont(font_b);
	text(" [ Ctrl ] + [ + ] ",20,160);
	text(" [ scrol ] ",20,180);

	textFont(font_a);
	text(" POMNIEJSZENIE ",20,200);
	textFont(font_b);
	text(" [ Ctrl ] + [ - ] ",20,220);
	text(" [ scrol ] ",20,240);

	textFont(font_a);
	text(" RESET ",20,260);
	textFont(font_b);
	text(" [ Shift ] + [ r ] ",20,280);

	textFont(font_a);
	text(" PRESETY ",20,300);
	textFont(font_b);
	text(" [ Shift ] + [ 1 - 9 ] ",20,320);

	text(" * [ LMB ] lewy przycisk myszy ",20,360);

	popMatrix();
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
