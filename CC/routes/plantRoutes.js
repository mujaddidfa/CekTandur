const express = require('express');
const router = express.Router();
const plantController = require('../controllers/plantController');

router.get('/', plantController.getAllPlants);
router.get('/:class', plantController.getPlantByClass);

module.exports = router;
