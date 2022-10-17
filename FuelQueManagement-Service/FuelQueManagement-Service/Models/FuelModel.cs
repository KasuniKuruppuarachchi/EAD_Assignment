using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace FuelQueManagement_Service.Models
{
    public class FuelModel
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }
        public string Type { get; set; }
        public int Amount { get; set; }
        public string Date { get; set; }
        public string Time { get; set; }
        public string? StationsId { get; set; }
        public DateTime? LastModified { get; set; }

        public FuelModel(string type, int amount, DateTime? lastModified)
        {
            Type = type;
            Amount = amount;
            LastModified = lastModified;
        }

        public FuelModel()
        {
         
        }
    }
}
