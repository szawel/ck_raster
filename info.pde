PFont font_a, font_b;
void info(){
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

	text(" - pojedyńczej lini ",20,150);
	text(" - pomiędazy liniami ",20,170);
	text(" - pomiedzy wierszami ",20,190);
	text(" - co drugiej lini ",20,210);
	text(" - loini ",20,230);

	text(" - wygiecia ",20,280);
	text(" - powtórzeń wygiecia ",20,300);
	text(" - ??? ",20,320);

	popMatrix();

	pushMatrix();	
	translate(700,50);
	textFont(font_a);
	text(" SHORTCUT ",20,60);
	
	text(" PRZESUWANIE",20,80);
	textFont(font_b);
	text(" [ space ] + [ LMB ]  i  [ h ] + [ LMB ]",20,100);
	// text(" [ h ] + [ LMB ] ",20,120);

	textFont(font_a);
	text(" POWIEKSZENIE ",20,140);
	textFont(font_b);
	text(" [ Ctrl ] + [ + ]  i  [ scrol ]",20,160);
	// text(" [ scrol ] ",20,180);

	textFont(font_a);
	text(" POMNIEJSZENIE ",20,200);
	textFont(font_b);
	text(" [ Ctrl ] + [ - ]  i  [ scrol ]",20,220);
	// text(" [ scrol ] ",20,240);

	textFont(font_a);
	text(" RESET ",20,260);
	textFont(font_b);
	text(" [ Shift ] + [ r ] ",20,280);

	textFont(font_a);
	text(" FORMATY ",20,300);
	textFont(font_b);
	text(" [ A ] + [ 0 - 6 ]  i  [ B ] + [ 0 - 6 ]",20,320);
	// text(" [ b ] + [ 0 - 6 ] ",20,340);

	text(" * [ LMB ] lewy przycisk myszy ",20,360);

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