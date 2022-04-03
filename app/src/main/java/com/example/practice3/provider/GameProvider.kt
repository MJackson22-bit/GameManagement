package com.example.practice3.provider

import com.example.practice3.model.Game

class GameProvider {
    companion object {
        var gameList = mutableListOf<Game>(
            Game(0, "Clash Royale", "Supercell", "Estrategia", "https://media.vandal.net/i/ivandal/1200x630/37900/clash-royale-2016330115744_6.jpg", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."),
            Game(1, "Fornite", "Epic Game", "Acción", "https://fs-prod-cdn.nintendo-europe.com/media/images/10_share_images/games_15/nintendo_switch_download_software_1/H2x1_NSwitchDS_Fortnite.jpg", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."),
            Game(2, "Call of Duty", "Activision", "Acción", "https://i.blogs.es/f307c9/mob0/1366_2000.jpeg", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")
        )
    }
}