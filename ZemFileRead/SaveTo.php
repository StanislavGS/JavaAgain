<?php

session_start();
echo '<meta http-equiv="content-type" content="text/html; charset=utf-8"/>';
if ($_GET['action'] == 6) {
    $wi = $_SESSION['wi'];
    $hei = $_SESSION['hei'];
    $im = @imagecreatetruecolor($wi, $hei)
            or die('Cannot Initialize new GD image stream');
    $red = imagecolorallocate($im, 0xFF, 0x00, 0x00);
    $black = imagecolorallocate($im, 0x00, 0x00, 0x00);
    $green = imagecolorallocate($im, 0x00, 0xFF, 0x00);
    imagefilledrectangle($im, 0, 0, $wi - 1, $hei - 1, $red);
    $font_file = 'SAFon.ttf';
    include_once 'ClassDisplay.php';
    $disl = unserialize($_SESSION['display']);
    $fon = imagecolorallocate($im, 0xD0, 0xD0, 0xD0);
    $drw = imagecolorallocate($im, 0x00, 0x00, 0xC0);
    imagefilledrectangle($im, 0, 0, $wi - 1, $hei - 1, $fon);
    if ($wi / $hei > ($disl->xr - $disl->xl) / ($disl->yg - $disl->yd)) {
        $sc = $hei / ($disl->yg - $disl->yd);
        $xcor = 0.5 * ($wi - $sc * ($disl->xr - $disl->xl));
        $ycor = 0;
    } else {
        $sc = $wi / ($disl->xr - $disl->xl);
        $ycor = 0.5 * ($hei - $sc * ($disl->yg - $disl->yd));
        $xcor = 0;
    }
    if ($disl->lines || $disl->texts) {
        foreach ($disl->lines as $ln) {
            imageline($im, $xcor + $sc * $ln->x1, $hei - $ycor - $sc * $ln->y1, 
                    $xcor + $sc * $ln->x2, $hei - $ycor - $sc * $ln->y2, $drw);
            //break;
        }
        if (count($disl->texts) > 0) {
            foreach ($disl->texts as $tx) {
                if (((int) $sc * 5) > 0)
                    imagefttext($im, (int) $sc * 5, 0, $xcor + $sc * $tx->x, $hei - $ycor - $sc * $tx->y, 
                            $drw, 'SAFon.ttf', $tx->textString);
                else
                    imageline($im, $xcor + $sc * $tx->x, $hei - $ycor - $sc * $tx->y, 
                            $xcor + $sc * $tx->x + 1, $hei - $ycor - $sc * $tx->y + 1, $drw);
                //break;
            }
        }
    }else {
        imagefttext($im, (int) 35, 0, 0.5 * ($wi - 175), 0.5 * ($hei - 35) + 35, 
                $red, 'SAFon.ttf', 'No Data!');
    }
    imagejpeg($im, './tmp/' . session_id() . '.jpg', 100);
    imagedestroy($im);

    echo 'Jpeg файла, може да свалите от следния  <a href="./tmp/' . session_id() . '.jpg">линк</a>';
    echo '<br/><br/><br/><br/><a href="zem-laptop.php?' . SID . '">Обратно.</a><br/><br/>';
}


if ($_GET['action'] == 7) {
    include_once 'classZemCad.php';
    $cl = unserialize($_SESSION['zem']);

    //var_dump($cl);
    $nameFile = './tmp/' . (session_id() + 5) . '.dxf';
    $dxfHandle = 16;

    $handle = @fopen($nameFile, "w");
    if ($handle) {
        fprintf($handle, "0\r\nSECTION\r\n2\r\nTABLES0\r\nTABLE\r\n2\r\nVPORT\r\n70\r\n1\r\n" .
                "0\r\nVPORT\r\n2\r\n*ACTIVE\r\n70\r\n0\r\n10\r\n0.0\r\n20\r\n0.0\r\n11\r\n1.0\r\n21\r\n1.0\r\n" .
                "12\r\n%.3f\r\n22\r\n%.3f\r\n130.0\r\n23\r\n0.0\r\n14\r\n10.0\r\n24\r\n10.0\r\n15\r\n".
                "10.0\r\n25\r\n10.0\r\n" .
                "16\r\n0.0\r\n26\r\n0.0\r\n36\r\n1.0\r\n17\r\n0.0\r\n27\r\n0.0\r\n37\r\n0.0\r\n40\r\n%.3f\r\n" .
                "41\r\n2.3245033112582778\r\n4250.0\r\n43\r\n0.0\r\n44\r\n0.0\r\n50\r\n0.0\r\n51\r\n0.0\r\n" .
                "71\r\n0\r\n72\r\n1000\r\n73\r\n1\r\n74\r\n3\r\n75\r\n0\r\n76\r\n1\r\n77\r\n0\r\n78\r\n0\r\n".
                "0\r\nENDTAB\r\n0\r\nENDSEC\r\n", 
                0.5 * ($cl->xWinLeft + $cl->xWinRight), 0.5 * ($cl->yWinBottom + $cl->yWinUp), 
                min(($cl->xWinRight - $cl->xWinLeft), ($cl->yWinUp - $cl->yWinBottom)));

        fprintf($handle, "0\r\nSECTION\r\n2\r\nENTITIES\r\n");
        foreach ($cl->lines as $ln) {
            //fprintf($handle,"0\r\nPOLYLINE\r\n5\r\n%X\r\n8\r\n%d\r\n10\r\n0.0\r\n20\r\n0.0\r\n30\r\n0.0\r\n",$dxfHandle++,$ln->type);
            fprintf($handle, "0\r\nPOLYLINE\r\n8\r\n%d\r\n 66\r\n     1\r\n", $ln->type);
            foreach ($ln->points as $poi) {
                //fprintf($handle,"0\r\nVERTEX\r\n5\r\n%X\r\n8\r\n%d\r\n10\r\n%.3f\r\n20\r\n%.3f\r\n30\r\n0.0\r\n",$dxfHandle++,$ln->type,$poi->x,$poi->y);
                fprintf($handle, "0\r\nVERTEX\r\n8\r\n%d\r\n10\r\n%.3f\r\n20\r\n%.3f\r\n", $ln->type, $poi->x, $poi->y);
            }
            //fprintf($handle,"0\r\nSEQEND\r\n5\r\n%X\r\n8\r\n%d\r\n",$dxfHandle++,$ln->type);	
            fprintf($handle, "0\r\nSEQEND\r\n8\r\n%d\r\n", $ln->type);
        }
        foreach ($cl->texts as $tx) {
            if (trim($tx->textString))
                fprintf($handle, "0\r\nTEXT\r\n8\r\n%s\r\n10\r\n%.3f\r\n20\r\n%.3f\r\n".
                        "30\r\n0.0\r\n40\r\n2.0\r\n" .
                        "1\r\n%s\r\n50\r\n%f\r\n11\r\n%.3f\r\n21\r\n%.3f\r\n31\r\n0.0\r\n",
                        'TEXT' . $tx->type, $tx->x, $tx->y, $tx->textString, 0, $tx->x, $tx->y);
        }
        fprintf($handle, "0\r\nENDSEC\r\n0\r\nEOF");
        fclose($handle);
        echo 'Dxf файла, може да свалите от следния  <a href="' . $nameFile . '">линк</a>';
    }
    else {
        echo('Gre6ka pri otvarjane na fajla za zapis');
    }
    echo '<br/><br/><br/><br/><a href="zem-laptop.php?' . SID . '">Обратно.</a><br/><br/>';
}
?>