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
