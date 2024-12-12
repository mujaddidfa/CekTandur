const fs = require('fs');
const path = require('path');
const plantsFilePath = path.join(__dirname, '../models/plant.json');

const readPlantsData = () => {
    if (!fs.existsSync(plantsFilePath)) {
        throw new Error("File not found");
    }
    const data = fs.readFileSync(plantsFilePath);
    return JSON.parse(data);
};

exports.getAllPlants = (req, res) => {
    try {
        const data = readPlantsData();
        res.json({
            error: false,
            message: "Plants data fetched successfully",
            plants: data.plants
        });
    } catch (error) {
        console.error("Error reading plants data:", error);
        res.status(500).json({
            error: true,
            message: "Internal server error. Could not retrieve plants data."
        });
    }
};

// Function to get plant data by idPlant
exports.getPlantById = (req, res) => {
    try {
        const data = readPlantsData();
        const plantId = parseInt(req.params.idPlant);  // Get the id from the URL parameter
        const plant = data.plants.find(plant => plant.idPlant === plantId);

        if (plant) {
            res.json({
                error: false,
                message: "Plant data fetched successfully",
                plant: plant
            });
        } else {
            res.status(404).json({
                error: true,
                message: `Plant with ID ${plantId} not found`
            });
        }
    } catch (error) {
        console.error("Error reading plants data:", error);
        res.status(500).json({
            error: true,
            message: "Internal server error. Could not retrieve plant data."
        });
    }
};

