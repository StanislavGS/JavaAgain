<?php

class Display {

    public $xl;
    public $yd;
    public $xr;
    public $yg;

    public function __construct($x1, $y1, $x2, $y2) {
        $this->xl = ($x1 > $x2) ? $x2 : $x1;
        $this->xr = ($x1 > $x2) ? $x1 : $x2;
        $this->yd = ($y1 > $y2) ? $y2 : $y1;
        $this->yg = ($y1 > $y2) ? $y1 : $y2;
    }

    public function DobavLine($x1, $y1, $x2, $y2) {//Vhodnite danni sa v globalni koordinati
        if ($x1 > $this->xr && $x2 > $this->xr) {
            return NULL;
        }
        if ($x1 < $this->xl && $x2 < $this->xl)
            return NULL;
        if ($y1 > $this->yg && $y2 > $this->yg)
            return NULL;
        if ($y1 < $this->yd && $y2 < $this->yd)
            return NULL;
        if (abs($x1 - $x2) < 0.00001) {
            $xs = 0.5 * ($x1 + $x2);
            if ($xs > $this->xr || $xs < $this->xl)
                return NULL;
            $this->lines[] = new DsplLine($xs - $this->xl, ($y1 < $y2 ? ($y1 < $this->yd ? $this->yd : $y1) : ($y1 > $this->yg ? $this->yg : $y1)) - $this->yd, $xs - $this->xl, ($y1 < $y2 ? ($y2 < $this->yg ? $y2 : $this->yg) : ($y2 > $this->yd ? $y2 : $this->yd)) - $this->yd);
        }else {
            $a = ($y2 - $y1) / ($x2 - $x1);
            $xr1 = $x1 - $this->xl;
            $xr2 = $x2 - $this->xl;
            $yr1 = $y1 - $this->yd;
            $yr2 = $y2 - $this->yd; //Preobrazuva v lokalni koordinati s na4alo - na4aloto na prozoreca
            $b = ($yr1 - $a * $xr1);
            if ($xr1 < 0) {
                $xr1 = 0;
                $yr1 = $b;
            }
            if ($xr1 > ($this->xr - $this->xl)) {
                $xr1 = ($this->xr - $this->xl);
                $yr1 = $a * $xr1 + $b;
            }
            if ($xr2 < 0) {
                $xr2 = 0;
                $yr2 = $b;
            }
            if ($xr2 > ($this->xr - $this->xl)) {
                $xr2 = ($this->xr - $this->xl);
                $yr2 = $a * $xr2 + $b;
            }
            if ($yr1 < 0) {
                $yr1 = 0;
                $xr1 = -$b / $a;
            }
            if ($yr1 > ($this->yg - $this->yd)) {
                $yr1 = ($this->yg - $this->yd);
                $xr1 = ($yr1 - $b) / $a;
            }
            if ($yr2 < 0) {
                $yr2 = 0;
                $xr2 = -$b / $a;
            }
            if ($yr2 > ($this->yg - $this->yd)) {
                $yr2 = ($this->yg - $this->yd);
                $xr2 = ($yr2 - $b) / $a;
            }
            if (abs($xr2 - $xr1) < 0.00005 && abs($yr2 - $yr1) < 0.00005) {
                return NULL;
            }
            $this->lines[] = new DsplLine($xr1, $yr1, $xr2, $yr2);
        }
    }

    public function DobavText($x1, $y1, $st) {
        $bbox = imageftbbox(5, 0, 'SAFon.ttf', $st);
        //var_dump($bbox);echo $st.'<br/>';
        if ($x1 < $this->xr && $y1 < $this->yg && $bbox[2] - $bbox[0] + $x1 > $this->xl && 
                $bbox[3] - $bbox[5] + $y1 > $this->yd) {
            $this->texts[] = new DsplText($x1 - $this->xl, $y1 - $this->yd, $st);
        }
    }

    public function DrawImage($im, $wi, $hei) {
        $fon = imagecolorallocate($im, 0xD0, 0xD0, 0xD0);
        $drw = imagecolorallocate($im, 0x00, 0x00, 0xC0);
        imagefilledrectangle($im, 0, 0, $wi - 1, $hei - 1, $fon);
        if ($wi / $hei > ($this->xr - $this->xl) / ($this->yg - $this->yd)) {
            $sc = $hei / ($this->yg - $this->yd);
            $xcor = 0.5 * ($wi - $sc * ($this->xr - $this->xl));
            $ycor = 0;
        } else {
            $sc = $wi / ($this->xr - $this->xl);
            $ycor = 0.5 * ($hei - $sc * ($this->yg - $this->yd));
            $xcor = 0;
        }
        foreach ($this->lines as $ln) {
            imageline($im, $xcor + $sc * $ln->x1, $hei - $ycor - $sc * $ln->y1, $xcor + $sc * $ln->x2, $hei - $ycor - $sc * $ln->y2, $drw);
        }
        foreach ($this->texts as $tx) {
            if (((int) $sc * 5) > 0) {
                imagefttext($im, (int) $sc * 5, 0, $xcor + $sc * $tx->x, $hei - $ycor - $sc * $tx->y, $drw, 'SAFon.ttf', $tx->textString);
            }
        }
    }

}

class DsplLine {

    public $x1, $y1, $x2, $y2;

    public function __construct($xr1, $yr1, $xr2, $yr2) {
        $this->x1 = $xr1;
        $this->y1 = $yr1;
        $this->x2 = $xr2;
        $this->y2 = $yr2;
    }

}

class DsplText {

    public $x, $y, $textString;

    public function __construct($xr, $yr, $st) {
        $this->x = $xr;
        $this->y = $yr;
        $this->textString = $st;
    }

}

?>