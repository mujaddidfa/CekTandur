
const multer = require('multer');
const path = require('path');
const { Storage } = require('@google-cloud/storage');
const admin = require('firebase-admin');
const db = admin.firestore();
const { v4: uuidv4 } = require('uuid');
const fs = require('fs'); 

exports.getUser = async (req, res) => {
  const { userId } = req.params; 
  try {
    const userDoc = await db.collection('users').doc(userId).get();

    if (!userDoc.exists) {
      return res.status(404).json({
        status: 404,
        message: "User not found",
        error: {
          details: "The user not found in database. Try creating an account.",
        },
      });
    }

    const userData = userDoc.data();

    return res.status(200).json({
      status: 200,
      message: "Received data successfully",
      data: userData,
    });
  } catch (error) {
    return res.status(500).json({
      status: 500,
      message: "Failed to fetch user",
      error: {
        details: error.message,
      },
    });
  }
};

exports.updateUser = async (req, res) => {
  const { userId } = req.params; 
  const updates = req.body;

  try {
    const userRef = db.collection('users').doc(userId);

    const userDoc = await userRef.get();

    if (!userDoc.exists) {
      return res.status(404).json({
        status: 404,
        message: "User not found",
        error: {
          details: "The user not found in database. Try creating an account.",
        },
      });
    }

    const updatedAt = new Date().toISOString();
    await userRef.update({ ...updates, updatedAt });

    const updatedDoc = await userRef.get();

    return res.status(200).json({
      status: 200,
      message: "User updated successfully",
      data: updatedDoc.data(),
    });
  } catch (error) {
    return res.status(500).json({
      status: 500,
      message: "Failed to update user",
      error: {
        details: error.message,
      },
    });
  }
}; 

exports.deleteUser = async (req, res) => {
  const { userId } = req.params; // Menggunakan userId dari params

  try {
    const userRef = db.collection('users').doc(userId);

    const userDoc = await userRef.get();

    if (!userDoc.exists) {
      return res.status(404).json({
        status: 404,
        message: "User not found",
        error: {
          details: "The user not found in database. Try creating an account.",
        },
      });
    }

    await userRef.delete();

    return res.status(200).json({
      status: 200,
      message: "User deleted successfully",
    });
  } catch (error) {
    return res.status(500).json({
      status: 500,
      message: "Failed to delete user",
      error: {
        details: error.message,
      },
    });
  }
};

const upload = multer({ storage: multer.memoryStorage() });
const storage = new Storage();
const bucketName = 'bucket-cek-tandur';
const bucket = admin.storage().bucket();
const plantData = JSON.parse(fs.readFileSync(path.join(__dirname, '../models/plant.json'), 'utf8'));

exports.savePlantHistory = async (req, res) => {
  const { userId, className, diseaseName, confidence } = req.body;

  if (!userId || !req.file) {
    return res.status(400).json({
      status: 400,
      message: "Missing required fields",
      error: {
        details: "Please provide userId and plantImage.",
      },
    });
  }

  try {
    const plantInfo = plantData.plants.find(
      (plant) => plant.className === className && plant.disease_name === diseaseName
    );

    if (!plantInfo) {
      return res.status(404).json({
        status: 404,
        message: "Plant information not found for the given className and diseaseName.",
      });
    }

    const fileName = `${userId}-history-${Date.now()}${path.extname(req.file.originalname)}`;
    const file = bucket.file(fileName);
    const stream = file.createWriteStream({
      metadata: { contentType: req.file.mimetype },
    });

    stream.on('error', (error) => {
      console.error("Failed to upload image:", error);
      return res.status(500).json({
        status: 500,
        message: "Failed to upload image",
        error: { details: error.message },
      });
    });

    stream.on('finish', async () => {
      const fileUrl = `https://storage.googleapis.com/${bucket.name}/${fileName}`;

      const timestamp = new Date().toLocaleString('en-US', {
        timeZone: 'Asia/Jakarta',
      });

      const historyId = uuidv4().replace(/-/g, '').slice(0, 16);

      const historyRef = db.collection('histories').doc(historyId);
      const historyData = {
        userId,
        className,
        diseaseName,
        description: plantInfo.description, 
        confidence,
        causes: plantInfo.causes, 
        treatments: plantInfo.treatments, 
        alternative_products: plantInfo.alternative_products, 
        alternative_products_links: plantInfo.alternative_products_links, 
        imageUrl: fileUrl,
        timestamp,
      };

      await historyRef.set(historyData);

      res.status(201).json({
        status: 201,
        message: "Plant history saved successfully",
        data: historyData,
      });
    });

    stream.end(req.file.buffer);
  } catch (error) {
    console.error("Error saving plant history:", error);
    res.status(500).json({
      status: 500,
      message: "Failed to save plant history",
      error: { details: error.message },
    });
  }
};

exports.getPlantHistories = async (req, res) => {
  const { userId } = req.params;

  console.log("Fetching histories for userId:", userId); 

  try {
    const historiesSnapshot = await db
      .collection('histories') 
      .where('userId', '==', userId) 
      .get();

    console.log("Histories snapshot size:", historiesSnapshot.size); 

    if (historiesSnapshot.empty) {
      return res.status(404).json({
        status: 404,
        message: "No histories found for this user",
        data: [],
      });
    }

    const histories = historiesSnapshot.docs.map(doc => ({
      ...doc.data(), 
    }));

    console.log("Histories:", histories); 

    res.status(200).json({
      status: 200,
      message: "Histories retrieved successfully",
      data: histories,
    });
  } catch (error) {
    console.error("Error retrieving plant histories:", error);
    res.status(500).json({
      status: 500,
      message: "Failed to retrieve plant histories",
      error: { details: error.message },
    });
  }
};

