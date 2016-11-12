<?php
header('Content-Type: text/html');//image/png');
session_start();
/*header ('Content-Type: image/png');
$im = @imagecreatetruecolor(500, 380)
      or die('Cannot Initialize new GD image stream');
$text_color = imagecolorallocate($im, 233, 14, 91);
imagestring($im, 1, 5, 5,  'A Simple Text String', $text_color);
imagestring($im, 2, 5, 25,  'A Simple Text String-2', $text_color);
imagestring($im, 3, 5, 45,  'A Simple Text String-3', $text_color);
imagestring($im, 4, 5, 65,  'A Simple Text String-4', $text_color);
imagestring($im, 5, 5, 85,  'A Simple Text String-5', $text_color);
imageline($im, 5, 5, 495, 375, $text_color);
imagepng($im);
imagedestroy($im);*/

// Create a 300x100 image

$wi=$_SESSION['wi']; $hei=$_SESSION['hei'];

$im = @imagecreatetruecolor($wi, $hei)
      or die('Cannot Initialize new GD image stream');///////////////////////

	  
	  
	  
$red = imagecolorallocate($im, 0xFF, 0x00, 0x00);
$black = imagecolorallocate($im, 0x00, 0x00, 0x00);
$green = imagecolorallocate($im, 0x00, 0xFF, 0x00);
//// Make the background red
imagefilledrectangle($im, 0, 0, $wi-1, $hei-1, $red);
//
//// Path to our ttf font file
$font_file = 'SAFon.ttf';
//
//// Draw the text 'PHP Manual' using font size 13


include_once 'ClassDisplay.php';///////////////////////
$disl = unserialize($_SESSION['display']);///////////////////////

///////////////////////$f=fopen('tmp.txt','w')
///////////////////////fprintf($f,'%s',serialize($disl));
///////////////////////fclose($f);
//var_dump($disl);
//imagefttext($im, 25, 0, 1, 55, $green, $font_file, $wi.'|'.$hei.'|'.$disl->xl.'|'.$disl->xr.'|'.$disl->yd.'|'.$disl->yg);

// Output image to the browser



///$disl->DrawImage(&$im,900,300);///////////////////////

/// DrawImage 2

$fon=imagecolorallocate($im, 0xD0, 0xD0, 0xD0);
$drw=imagecolorallocate($im, 0x00, 0x00, 0xC0);
imagefilledrectangle($im, 0, 0, $wi-1, $hei-1, $fon);



if ($wi/$hei>($disl->xr-$disl->xl)/($disl->yg-$disl->yd)){
	$sc=$hei/($disl->yg-$disl->yd);
	$xcor=0.5*($wi-$sc*($disl->xr-$disl->xl));$ycor=0;
}else{
	$sc=$wi/($disl->xr-$disl->xl);
	$ycor=0.5*($hei-$sc*($disl->yg-$disl->yd));$xcor=0;
}
//imagefttext($im, 25, 0, 1, 55, $green, $font_file, $sc.'|'.$xcor.'|'.$ycor);
//imagefttext($im, 25, 0, 1, 85, $green, $font_file, $disl->lines[0]->x1.'|'.$disl->lines[0]->x2.'|'.$disl->lines[0]->y1.'|'.$disl->lines[0]->y2);
//imagefttext($im, 25, 0, 1, 85, $green, $font_file,count($disl->texts).'|'.$disl->texts[0]->x.'|'.$disl->texts[0]->y.'|'.$disl->texts[0]->st);
if ($disl->lines || $disl->texts) {
	foreach($disl->lines as $ln) {
		imageline($im,$xcor+$sc*$ln->x1,$hei-$ycor-$sc*$ln->y1,$xcor+$sc*$ln->x2,$hei-$ycor-$sc*$ln->y2,$drw);
		//break;
	}
	if (count($disl->texts)>0){
		foreach($disl->texts as $tx) {
			if (((int) $sc*5)>0 ) imagefttext($im, (int) $sc*5, 0, $xcor+$sc*$tx->x, $hei-$ycor-$sc*$tx->y, $drw, 'SAFon.ttf', $tx->textString);
			else imageline($im,$xcor+$sc*$tx->x,$hei-$ycor-$sc*$tx->y,$xcor+$sc*$tx->x+1,$hei-$ycor-$sc*$tx->y+1,$drw);
			//break;
		}
	}
}else{
	imagefttext($im, (int) 35, 0, 0.5*($wi-175), 0.5*($hei-35)+35, $red, 'SAFon.ttf', 'No Data!');
}






imagepng($im);
imagedestroy($im);

?>