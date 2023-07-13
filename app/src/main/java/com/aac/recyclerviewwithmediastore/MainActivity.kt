package com.aac.recyclerviewwithmediastore

import android.content.pm.PackageManager
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aac.recyclerviewwithmediastore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private val galleryAdapter: GalleryAdapter = GalleryAdapter()
    private val mediaStoreHelper: IMediaStore = MediaStoreHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        // Recyclerview adapter
        recyclerView = binding.rvGallery
        recyclerView.adapter = galleryAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 120)
        }
        else
        {
            populateGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        populateGallery()
    }

    private fun populateGallery()
    {
        // MediaStore
        galleryAdapter.setItems(mediaStoreHelper.getAllMedia())
    }
}