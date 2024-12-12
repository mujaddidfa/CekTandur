const express = require('express');
const router = express.Router();
const { getAllPlants, getPlantById} = require('../controllers/plantController');

router.get('/', getAllPlants);
router.get('/:idPlant', getPlantById);

module.exports = router;
