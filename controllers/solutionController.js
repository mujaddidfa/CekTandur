// const { db } = require('../config/config');

// const getSolutions = async (req, res) => {
//     try {
//         const snapshot = await db.collection('plants').get();
//         const plants = snapshot.docs.map(doc => doc.data());
//         res.status(200).json(plants);
//     } catch (error) {
//         res.status(500).json({ error: error.message });
//     }
// };

// module.exports = { getSolutions };
                