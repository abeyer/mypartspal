package com.example.mypartspal

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.mypartspal.brotherprinter.QLPrinterUtil
import com.example.mypartspal.databinding.FragmentSecondBinding
import com.example.mypartspal.pbapi.Part
import com.example.mypartspal.pbapi.Storage
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.io.File

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var barcodeLauncher: ActivityResultLauncher<ScanOptions>? = null
    private var _part: Part? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            if (result.contents == null) {
                Log.e("", "No scan result")
            } else {
                Log.w("", "Scanned: " + result.contents)
                loadPart(result.contents)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanButton.setOnClickListener {
            barcodeLauncher!!.launch(ScanOptions())
        }
        binding.checkoutButton.setOnClickListener {
            checkoutPart(_part!!.partId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        barcodeLauncher = null
    }

    private fun checkoutPart(partId: String) {
        val storageId: String = getProjectStorage().storageId
        binding.checkoutButton.setEnabled(false)
        movePart(partId, storageId)
        printPartLabel(_part!!, storageId)
        clearPart()
    }

    private fun printPartLabel(part: Part, storageId: String) {
        //TODO: PartsBoxAPI.getProjectPartDesignation()
        val projectDesignation: String = "PRT42"
        QLPrinterUtil.printLabel(part.partId, storageId, part.partName, projectDesignation, getAdapter(), getWorkDir())
    }

    private fun clearPart() {
        binding.partText.text = "No part found"
        _part = null
        binding.checkoutButton.setEnabled(false)
    }

    private fun loadPart(partCode: String) {
        binding.partText.text = partCode
        // TODO: partId, partLocation = partCode.Split(":")
        // TODO: _part = PartsBoxAPI.getPart(partId)
        _part = Part("fakepartid", "NEXPERIA - PMEG4010EGWX - Schottky Rectifier")
        binding.checkoutButton.setEnabled(true)
    }

    private fun movePart(partId: String, projectStorageId: String) {
        // TODO: PartsBoxAPI.movePart(_part, projectStorageId)
    }

    private fun getProjectStorage(): Storage {
        // TODO: PartsBoxAPI.getOrCreateStorage(userId, projectId)
        return Storage("fakestorageid")
    }

    fun getAdapter(): BluetoothAdapter {
        val ctx = requireContext()
        val mgr: BluetoothManager =
            ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        return mgr.adapter
    }

    fun getWorkDir(): File {
        val ctx = requireContext()
        return ctx.cacheDir
    }
}