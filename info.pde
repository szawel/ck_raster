PFont font_b,font_c;
void info(){
	/*
	menu info opisy 
	*/

	font_b = loadFont("PTSansPro-Regular-12.vlw");
	
	fill(255);
	strokeWeight(1);
	stroke(0);
	
	// tło menu info
	rect(0,50,950,430);

	// --------------------------------------------- [ opis do menu ]
	noStroke();
	fill(0);

	pushMatrix();	
	translate(415,51);
	

	textFont(font_b);
	text(" – obszaru roboczego i obszaru eksportu ",43,80);
	text(" – obszaru roboczego i obszaru eksportu ",41,100);

	text(" – pojedyńczej linii ",33,150);
	text(" – pomiędzy liniami ",26,170);
	text(" – pomiędzy wierszami ",40,190);
	text(" – co drugiej linii ",20,210);
	text(" – linii ",33,230);

	text(" – sinusa ",43,280);
	text(" – powtórzeń sinusa ",63,300);
	text(" – wygięcia ",57,320);

	popMatrix();

	// --------------------------------------------- [ opis do skrótów klawiaturowych ]
	pushMatrix();	
	translate(680,50);

	text(" SHORTCUT ",20,60);
	
	text(" przesuwanie",20,80);
	text(" [ space ] + [ LMB ]  lub  [ h ] + [ LMB ]",20,100);

	text(" powiększanie | zoom in",20,140);
	text(" [ Ctrl ] + [ + ]  lub  [ scrol ]",20,160);

	text(" pomniejszanie | zoom out ",20,200);
	text(" [ Ctrl ] + [ - ]  lub  [ scrol ]",20,220);

	text(" formaty drukarskie ",20,260);
	text(" [ A ] + [ 0 – 6 ]  i  [ B ] + [ 0 – 6 ]",20,280);

	text(" viewport reset ",20,320);
	text(" [ Shift ] + [ r ] ",20,340);

	text(" * [ LMB ] lewy przycisk myszy ",20,380);

	popMatrix();
}


void ws_info(){
	/*
	boczne menu z informacja
	o szerokości i wysokości
	z przeliczeniem na 'mm'
	*/

  ws_info_txt = createFont("PT Sans Pro",12);
  textFont(ws_info_txt);
  textAlign(LEFT);
  
  fill(0);
  pushMatrix();
  translate(ws_width,-10);
  text(" szerokość ",10,20);
  text(ws_width,10,40);
  text(" px ", 70,40);
  text(ws_width * cal,10,60);
  text(" mm ", 70,60);

  text(" wysokość ",10,100);
  text(ws_height,10,120);
  text(" px ", 70,120);
  text(ws_height * cal,10,140);
  text(" mm ", 70,140);

  popMatrix();
  noFill();
}

void info_b(){
	/*
	boczne menu z informacja
	o klatkarzu i o zoomie w procentach
	*/

	fill(0);
	font_c = loadFont("PTSansPro-Regular-12.vlw");
	textFont(font_c);
	textAlign(RIGHT);
	text(" fps : " + int(frameRate) + " | " + " zoom : " + int(zoom * 100) + " %",width-10,20);
}

void preset_info(String str, float _con){
	/*
	popup info o wybranym formacie drukarski
	*/

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

