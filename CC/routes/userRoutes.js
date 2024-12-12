const express = require('express');
const { getUser, updateUser, deleteUser, savePlantHistory, getPlantHistories } = require('../controllers/userController'); 
const multer = require('multer'); 
const upload = multer({ storage: multer.memoryStorage() }); 

const router = express.Router();

router.get('/:userId', getUser);
router.put('/:userId', updateUser);
router.delete('/:userId', deleteUser);
router.post('/history', upload.single('plantImage'), savePlantHistory);
router.get('/history/:userId', getPlantHistories); 

module.exports = router;
