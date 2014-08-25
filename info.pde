PFont font_a, font_b,font_c;
void info(){
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

	text(" - pojedyńczej lini ",33,150);
	text(" - pomiędazy liniami ",22,170);
	text(" - pomiedzy wierszami ",40,190);
	text(" - co drugiej lini ",20,210);
	text(" - loini ",33,230);

	text(" - wygiecia ",40,280);
	text(" - powtórzeń wygiecia ",63,300);
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
	text(" powiększanie | zoom in",20,140);
	textFont(font_b);
	text(" [ Ctrl ] + [ + ]  lub  [ scrol ]",20,160);
	// text(" [ scrol ] ",20,180);

	// textFont(font_a);
	text(" pomniejszanie | zoom out ",20,200);
	textFont(font_b);
	text(" [ Ctrl ] + [ - ]  lub  [ scrol ]",20,220);
	// text(" [ scrol ] ",20,240);

	// textFont(font_a);
	text(" presety formatów ",20,260);
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


void ws_info(){


  ws_info_txt = createFont("PT Sans Pro",12);
  textFont(ws_info_txt);
  textAlign(LEFT);
  
  fill(0);
  pushMatrix();
  translate(ws_width,-10);
  text(" szerokość ",10,20);
  text(ws_width,10,40);
  text(" px ", 70,40);
  text(ws_width * cal,10,50);
  text(" mm ", 70,50);

  text(" wysokość ",10,80);
  text(ws_height,10,100);
  text(" px ", 70,100);
  text(ws_height * cal,10,110);
  text(" mm ", 70,110);

  popMatrix();
  noFill();
}

void info_b(){
	fill(0);
	font_c = loadFont("PTSansPro-Regular-12.vlw");
	textFont(font_c);
	textAlign(RIGHT);
	text(" fps : " + int(frameRate) + " | " + " zoom : " + int(zoom * 100) + " %",width-10,20);
}

void preset_info(String str, float _con){
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

