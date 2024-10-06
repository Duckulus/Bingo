const fetch = require("node-fetch");
const fs = require("fs");

const version = "1.21";
const url = "https://api.github.com/repos/InventivetalentDev/minecraft-assets/contentsassets/minecraft/textures/item?ref=" + version;

const banneditems = [
    "barrier",
    "knowledge_book",
    "command_block_minecart",
    "command_block",
    "elytra",
    "empty",
    "overlay",
    "spawner",
    "structure_void",
    "turtle",
    "spawn_egg",
    "model",
    "compass",
    "music_disc",
    "clock",
    "debug",
    "list",
    "light",
    "fishing_rod_cast",
    "filled_map_markings",
    "crossbow_",
    "bow_",
    "fire",
    "base",
    "head",
    "bundle_filled"
]

function checker(value) {
    let result = false;
    banneditems.forEach(item => {
        if (value.includes(item)) {
            return result = true;
        }
    });
    if(result) return true;
    return false;
  }

fetch(url).then(res => res.json()).then(json => {
    json.forEach(item => {
        if (!checker(item.name.replace(".png", ""))) {
            console.log(`${String(item.name).replace(".png", "").toUpperCase()}("${String(item.name).toLowerCase().replace(".png", "").replaceAll("_", " ")}", Material.${String(item.name).replace(".png", "").toUpperCase()}),`)
            fetch(item.download_url).then(res => res.buffer()).then(buffer => {
                fs.writeFileSync(`./assets/${item.name}`, buffer);
                //console.log(`Downloaded ${item.name}`);
            });
        }
    });
});