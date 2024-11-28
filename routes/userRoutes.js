const express = require('express');
const {
  getUser,
  updateUser,
  deleteUser,
} = require('../controllers/userController');
const router = express.Router();

router.get('/:id', getUser);
router.put('/:id', updateUser);
router.delete('/:id', deleteUser);

module.exports = router;