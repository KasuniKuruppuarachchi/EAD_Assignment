using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace FuelQueManagement_Service.Models
{
    public class QueueModel
    {

        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public ObjectId Id { get; set; }
        public string VehicleType { get; set; }
        public int VehicleOwner { get; set; }
        public DateTime? LastModified { get; set; }

        public QueueModel(string vehicleType, int owner, DateTime? lastModified)
        {
            VehicleType = vehicleType;
            VehicleOwner = owner;
            LastModified = lastModified;
        }

        public QueueModel()
        {
        }
    }
}
