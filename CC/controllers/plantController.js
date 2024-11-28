// const { analyzePlant } = require('../services/mlService');

// const analyzeImage = async (req, res) => {
//     const { imageUrl } = req.body;

//     try {
//         const result = await analyzePlant(imageUrl);
//         if (result.error) return res.status(404).json({ error: result.error });

//         res.status(200).json({ plant: result });
//     } catch (error) {
//         res.status(500).json({ error: error.message });
//     }
// };

// module.exports = { analyzeImage };
