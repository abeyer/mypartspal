package com.example.mypartspal.brotherprinter

import android.R
import android.bluetooth.BluetoothAdapter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.brother.sdk.lmprinter.*
import com.brother.sdk.lmprinter.setting.QLPrintSettings
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File


class QLPrinterUtil {
    companion object {
        fun printBitmap(adapter: BluetoothAdapter, workDir: File, bitmap: Bitmap) {
            val channel = Channel.newBluetoothChannel("60:98:66:A0:DC:40", adapter)
            val result = PrinterDriverGenerator.openChannel(channel)
            if (result.error.code != OpenChannelError.ErrorCode.NoError) {
                Log.e("", "Error - Open Channel: " + result.error.code)
                return
            }
            Log.d("", "Success - Open Channel")

            val printerDriver = result.driver
            //
            // Put any code to use printer
            //
            val printSettings = QLPrintSettings(PrinterModel.QL_1110NWB)

            printSettings.labelSize = QLPrintSettings.LabelSize.RollW62
            printSettings.isAutoCut = true
            printSettings.workPath = workDir.toString()

            val printError: PrintError = printerDriver.printImage(bitmap, printSettings)

            if (printError.code != PrintError.ErrorCode.NoError) {
                Log.d("", "Error - Print Image: " + printError.code)
            } else {
                Log.d("", "Success - Print Image")
            }

            //
            // Put any code to use printer
            //
            printerDriver.closeChannel()
        }

        fun printLabel(partId: String, storageId: String, partDescription: String, projectDesignation: String, adapter: BluetoothAdapter, workDir: File) {
            val bmp: Bitmap = Bitmap.createBitmap(300, 200, Bitmap.Config.ARGB_8888)
            val canvas: Canvas = Canvas(bmp)

            val paint = Paint()
            paint.setColor(Color.BLACK)
            canvas.drawColor(Color.WHITE)
            paint.setTextSize(30f)
            canvas.drawText(
                "PRT42",
                145f,
                60f,
                paint
            )
            paint.setTextSize(14f)
            canvas.drawText(
                "NEXPERIA - PMEG4010EGWX - Schottky Rectifier",
                20f,
                160f,
                paint
            )

            val bitmap = BarcodeEncoder().encodeBitmap("$partId:$storageId", BarcodeFormat.QR_CODE, 140, 140)
            canvas.drawBitmap(bitmap, 0f, 0f, paint)

            printBitmap(adapter, workDir, bmp)
        }
    }
}