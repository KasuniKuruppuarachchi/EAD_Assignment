using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace FuelQueManagement_Service.Models;

public class FuelStationModel
{
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string? Id { get; set; }
    public string Name { get; set; }
    public string Location { get; set; }
    public FuelModel[] Fuel { get; set; }
    public QueueModel[] Queue { get; set; }
    public string StationOwner { get; set; }
    public bool FuelStatus { get; set; }
    public DateTime? LastModified { get; set; }

    public FuelStationModel(string name, string location, FuelModel[]? fuel, QueueModel[]? queue, string stationOwner, DateTime? lastModified)
    {
        Name = name;
        Location = location;
        Fuel = fuel;
        Queue = queue;
        StationOwner = stationOwner;
        LastModified = lastModified;
    }

    public FuelStationModel()
    {
    }
}
