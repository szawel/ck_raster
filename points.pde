// funkcja usrala koordynaty puknktów potrzebne 
float[][] points(float _y){
	float x , y;
	float nr = (sin_freq*6);

	float sep = ( ws_width / (nr));

	float[][]ppos = new float[int(nr+1)][int(nr+1)];

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

void qcur(float[][] _pos){

	// text(_pos.length,50,50);
	noFill();
	stroke(0);
	strokeWeight(1);

	beginShape();
	for(int i = 0; i < _pos.length-6; i+=6){
		vertex(_pos[0][i],_pos[1][i]);
		bezierVertex(_pos[0][i+1],_pos[1][i+1],_pos[0][i+2],_pos[1][i+2],_pos[0][i+3],_pos[1][i+3]);
		bezierVertex(_pos[0][i+4],_pos[1][i+4],_pos[0][i+5],_pos[1][i+5],_pos[0][i+6],_pos[1][i+6]);
	}
	endShape();
}