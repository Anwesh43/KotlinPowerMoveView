package ui.anwesome.com.kotlinpowerbuttonmoveview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.powerbuttonmoveview.PowerButtonMoveView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PowerButtonMoveView.create(this)
    }
}
