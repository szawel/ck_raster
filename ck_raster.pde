// m_ratser - ck raster generator //
// generator rastra //

//gui setup
float bg_color = 255;
float ws_bg_color = 200;

float ws_width = 300;
float ws_height = 500;

float zoom;
PVector offset;
PVector poffset;
PVector mouse;
boolean drag = false;

void setup() {
	frameRate(30);
	size(displayWidth, displayHeight);

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
	
	popMatrix();

	temp_info();


//	temp
	

}

void ws_display(){
	fill(ws_bg_color);
	rect(0,0,ws_width,ws_height);
	line_grid(ws_width,ws_height,100);
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
	strokeWeight(1);
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
	strokeWeight(1);
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
	text(frameRate,50,20);

	popMatrix();
}
