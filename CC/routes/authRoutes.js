const express = require('express');
const { registerUser, loginUser, logoutUser } = require('../controllers/authController');
const router = express.Router();

router.post('/register', registerUser);
router.post('/login', loginUser);
router.post('/logout', logoutUser);

module.exports = router;
