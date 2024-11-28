const admin = require("firebase-admin");
const serviceAccount = require("../ServiceAccount_Firebase.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: process.env.URLDATABASE,
});

const db = admin.firestore();
module.exports = { admin, db };