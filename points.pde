// funkcja usrala koordynaty puknktów potrzebne 
float[][] points(float _y,boolean seg_nr){
	float x , y;
	float nr = (sin_freq*6);

	float sep = ( ws_width / (nr));

	float[][]ppos = new float[int(nr+1)][int(nr+1)];
	float[][]new_ppos = new float[int(nr+1)][int(nr+1)];


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

void qcur(float[][] _pos){

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

float[][] sub_seg(float[][] _in, float ppos){

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

float sub(float _in_a, float _in_b, float _pos){
	float out =	map(_pos,0,1,_in_a,_in_b);

	return out;

}