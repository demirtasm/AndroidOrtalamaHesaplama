package com.example.muko.kotlinadapterortalamahesaplama

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.yeni_ders_layout.view.*

class MainActivity : AppCompatActivity() {
    var derslerArrays = arrayListOf("Matematik", "Fizik1", "Fizik2", "Diferansiyal", "Algoritma", "Java", "Kotlin", "Biyoloji", "Elektik ve Elektronik")
    var tumderslerHakkinda: ArrayList<Dersler> = ArrayList(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, derslerArrays)

        edt_ders_adi.setAdapter(adapter)

        btn_hesap.visibility = View.INVISIBLE

        btn_ekle.setOnClickListener {
            if (!edt_ders_adi.text.isNullOrEmpty()) {
                var inflater = LayoutInflater.from(this)
                var yenidersView = inflater.inflate(R.layout.yeni_ders_layout, null)

                yenidersView.edt_yeni_ders_adi.setAdapter(adapter)

                var dersAdi = edt_ders_adi.text.toString()
                var dersKredi = spinner_kredi.selectedItem.toString()
                var dersHarfNotu = spinner_harfnotu.selectedItem.toString()

                yenidersView.edt_yeni_ders_adi.setText(dersAdi)
                yenidersView.yeniKredi_spinner.setSelection(spinnerDegeriIndexiniBul(spinner_kredi, dersKredi))
                yenidersView.yeniHarfNotu_spinner.setSelection(spinnerDegeriIndexiniBul(spinner_harfnotu, dersHarfNotu))



                yenidersView.btn_sil.setOnClickListener {
                    linear_layout.removeView(yenidersView)
                    hesaplaButtonControl()
                }
                linear_layout.addView(yenidersView)
                hesaplaButtonControl()
                sifirla()
            } else {
                FancyToast.makeText(this," Ders Adını Girin... !",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
            }

        }

    }

    fun spinnerDegeriIndexiniBul(spinner: Spinner, aranacakDeger: String): Int {
        var index = 0
        for (i in 0..spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(aranacakDeger)) {
                index = i
                break
            }
        }
        return index
    }

    fun sifirla() {
        edt_ders_adi.setText("")
        spinner_kredi.setSelection(0)
        spinner_harfnotu.setSelection(0)
    }

    fun hesaplaButtonControl() {
        if (linear_layout.childCount == 0) {
            btn_hesap.visibility = View.INVISIBLE
        } else {
            btn_hesap.visibility = View.VISIBLE
        }
    }

    fun harfiNotaCevir(gelenNotHarfDegeri: String): Double {
        var deger: Double = 0.0
        when (gelenNotHarfDegeri) {
            "AA" -> deger = 4.0
            "BA" -> deger = 3.5
            "BB" -> deger = 3.0
            "CB" -> deger = 2.5
            "CC" -> deger = 2.0
            "DC" -> deger = 1.5
            "DD" -> deger = 1.0
            "FF" -> deger = 0.0
        }
        return deger
    }

    fun btn_ortalamaHesapla(view: View) {
        var toplamNot: Double = 0.0
        var toplamKredi: Double = 0.0

        for (i in 0..linear_layout.childCount - 1) {

            var tekSatir = linear_layout.getChildAt(i)

            var geciciDers = Dersler(
                tekSatir.edt_yeni_ders_adi.text.toString(),
                ((tekSatir.yeniKredi_spinner.selectedItemPosition) + 1).toString(),
                tekSatir.yeniHarfNotu_spinner.selectedItem.toString()
            )

            tumderslerHakkinda.add(geciciDers)
        }
        for (a in tumderslerHakkinda) {
            toplamNot += harfiNotaCevir(a.desHarfNot) * (a.dersKredi.toDouble())
            toplamKredi += a.dersKredi.toDouble()
        }
        FancyToast.makeText(this,"Ortalamanız : " + (toplamNot / toplamKredi),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show()
        tumderslerHakkinda.clear()
    }

}