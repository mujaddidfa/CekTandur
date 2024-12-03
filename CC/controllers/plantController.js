const fs = require('fs');
const path = require('path');
const plantsFilePath = path.join(__dirname, '../models/plant.json');

// Helper function to read the JSON file
const readPlantsData = () => {
    const data = fs.readFileSync(plantsFilePath);
    return JSON.parse(data);
};

// GET all plants
exports.getAllPlants = (req, res) => {
    const data = readPlantsData();
    res.json(data.plants);
};

// Mendapatkan tanaman berdasarkan nama penyakit
exports.getPlantByDiseaseName = (req, res) => {
    const diseaseName = req.params.disease_name.toLowerCase();
    const plants = readPlantsData().plants; // Memanggil plantsData melalui readPlantsData
    
    const result = plants.find(plant => 
        plant.disease_name.toLowerCase() === diseaseName
    );

    if (result) {
        res.json(result);
    } else {
        res.status(404).json({ message: 'Tanaman dengan nama penyakit tersebut tidak ditemukan' });
    }
};

// Mendapatkan tanaman berdasarkan nama tanaman
exports.getPlantByName = (req, res) => {
    const plantName = req.params.plant_name.toLowerCase();
    const plants = readPlantsData().plants; // Memanggil plantsData melalui readPlantsData

    // Mencari tanaman berdasarkan nama yang mirip dengan disease_name
    const result = plants.find(plant => 
        plant.name.toLowerCase().includes(plantName) // Cari berdasarkan nama tanaman
    );

    if (result) {
        res.json(result);
    } else {
        res.status(404).json({ message: 'Tanaman dengan nama tersebut tidak ditemukan' });
    }
};

// GET plant by class
exports.getPlantByClass = (req, res) => {
    const data = readPlantsData();
    const plantClass = req.params.class;
    const plant = data.plants[plantClass];

    if (plant) {
        res.json(plant);
    } else {
        res.status(404).json({ message: "Plant class not found" });
    }
};
