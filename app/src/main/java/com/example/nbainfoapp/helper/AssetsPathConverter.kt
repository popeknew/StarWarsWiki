package com.example.nbainfoapp.helper

class AssetsPathConverter() {

    fun createAssetsAddress(name: String): String {
        val newName = createImageName(name)
        return "file:///android_asset/$newName.jpg"
    }

    private fun createImageName(text: String): String {
        val newText = text.replace("-", "")
            .replace(" ", "")
            .replace("é", "e")
            .toLowerCase()
            .trim()
        return newText
    }
}