// m_ratser - ck raster generator //
// generator rastra //

import controlP5.*;
ControlP5 cp5;

//gui setup
float bg_color = 255;
float ws_bg_color = 200;

// --------------------------------------------- [ przestrzen robocza ]
float ws_width = 300;
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

float zoom;
PVector offset;
PVector poffset;
PVector mouse;
boolean drag = false;

void setup() {
	frameRate(30);
	size(displayWidth, displayHeight);

	smooth();
  noStroke();  
  cp5 = new ControlP5(this);
	PFont p = loadFont("PTSansPro-Regular-9.vlw");

	cp5.setControlFont(p);
  cp5.setColorLabel(#000000);
  cp5.setColorForeground(#000000);
  cp5.setColorBackground(#B4B4B4);
  cp5.setColorActive(#838383);

  // --------------------------------------------- [ przestrzen robocza ]

	float ws_menu_x = 50;
	float ws_menu_y = 100;
	float ws_menu_s = 20;
	int ws_menu_w = 350;
	int ws_menu_h = 15;

	cp5.addTextlabel("workspace")
		.setText("PRZESTRZEŃ ROBOCZA")
		.setPosition(ws_menu_x,ws_menu_y)
		// .setFont(loadFont("PTSansPro-Regular-12.vlw"))
		.setColorValue(#000000);

	cp5.addSlider("ws_width")
		.setCaptionLabel("szerokość")
		.setPosition(ws_menu_x,ws_menu_y+ws_menu_s)
		.setSize(ws_menu_w,ws_menu_h)
		.setRange(300,1000)
    ;

	cp5.addSlider("ws_height")
		.setCaptionLabel("wysokość")
		.setPosition(ws_menu_x,ws_menu_y+(ws_menu_s*2))
		.setSize(ws_menu_w,ws_menu_h)
		.setRange(300,1000)
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
		.setColorValue(#000000);

	cp5.addSlider("l_len")
		.setCaptionLabel("długość")
		.setPosition(linia_menu_x,linia_menu_y+linia_menu_s)
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(100,1000)
    ;

	cp5.addSlider("l_int_val")
		.setCaptionLabel("odtęp")
		.setPosition(linia_menu_x,linia_menu_y+(linia_menu_s*2))
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(100,1000)
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
		.setCaptionLabel("grubość")
		.setPosition(linia_menu_x,linia_menu_y+(linia_menu_s*5))
		.setSize(linia_menu_w,linia_menu_h)
		.setRange(100,1000)
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
		.setColorValue(#000000);

	cp5.addSlider("sin_amp")
		.setCaptionLabel("amplituda")
		.setPosition(sin_menu_x,sin_menu_y+sin_menu_s)
		.setSize(sin_menu_w,sin_menu_h)
		.setRange(-100,100)
    ;

	cp5.addSlider("sin_freq")
		.setCaptionLabel("czestotliwość")
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
	// zoom end position offset   

  zoom = 1.0;
  offset = new PVector((displayWidth)/2, (displayHeight)/2);
  poffset = new PVector(0, 0);
}
 
void draw() {
	background(bg_color);
	line_grid();

	pushMatrix();
	scale(zoom);
	translate(offset.x/zoom, offset.y/zoom);
	
	ws_display();
	
	// float dupa = ws_height / l_int_lin;
	for(int i = 0 ; i < ( ws_height / l_int_lin ); i++){
		qcur(points(i*l_int_lin));
	}




	popMatrix();

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

void ws_display(){
	fill(ws_bg_color);
	rect(0,0,ws_width,ws_height);
	line_grid(ws_width,ws_height,50);
}

void keyPressed(){

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
		zoom += 0.1;
	// println("[   zoom   ]"+" + 0.1 ");
	} 
	else if (key == '-') {
		zoom -= 0.1;
	// println("[   zoom   ]"+" - 0.1 ");
	}
} 

void keyReleased() {
  if (key == 'h' || key == 'H' || key == ' ') {
    drag = false;
  }
}

// Store the mouse and the previous offset
void mousePressed() {
  if ( drag == true) {
    mouse = new PVector(mouseX, mouseY);
    poffset.set(offset);
  }
}

// Calculate the new offset based on change in mouse vs. previous offsey
void mouseDragged() {
  if ( drag == true) {
    offset.x = mouseX - mouse.x + poffset.x;
    offset.y = mouseY - mouse.y + poffset.y;
  }
}

void mouseWheel(MouseEvent event) {
  float e = event.getAmount();
  if ( e == -1.0) {
    zoom += 0.1;
  }
  if (e == 1.0) {
    zoom -= 0.1;
  }
  if (zoom <= 0.1) {
    zoom = 0.1;
  }
}


// ------------------------------------------------------------ temp 
void line_grid(){
	float x = 0;
	float y = 0;
	float step = 100;
	float sep = 100;

	fill(0);
	strokeWeight(0.1);
	stroke(0);
	// kreski pionowe
	for(int i = 0; i < width/step; i++){
		x = i * step;
		line(x,0,x,height);
		text(int(x),x,10);
	}
	// kreski poziome

	for(int i = 0; i < height/step; i++){
		y = i * step;
		line(0,y,width,y);
		text(int(y),10,y);
	}

	// kursor myszy z koordynatami
	stroke(255);
	text(mouseX + ": x cor", mouseX + 20, mouseY);
	text(mouseY + ": y cor", mouseX + 20, mouseY + 15);



}

void line_grid(float w_x, float w_y, int sep)	{
	noFill();
	strokeWeight(0.1);
	stroke(0);
	float x = 0;
	float y = 0;
	fill(0);
	for(int i = 1; i < (w_x/sep); i++ ){
		x = i * sep;
		line(x,0,x,w_y);
		text(int(x),x,10);
	}

	for(int i = 1; i < (w_y/sep); i++ ){
		y = i * sep;
		line(0,y,w_x,y);
		text(int(y),10,y);
	}

}

void temp_info() {
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

