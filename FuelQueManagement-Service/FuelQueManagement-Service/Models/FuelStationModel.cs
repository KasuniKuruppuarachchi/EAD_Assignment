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
    public FuelModel[]? Fuel { get; set; }
    public QueueModel[]? Queue { get; set; }
    public string StationOwner { get; set; }
    public bool DieselStatus { get; set; }
    public bool PetrolStatus { get; set; }
    public int TotalPetrol { get; set; }
    public int TotalDiesel { get; set; }
    public QueueModel[]? QueueHistory { get; set; }
    public string? LastModified { get; set; }

    public FuelStationModel(string name, string location, FuelModel[]? fuel, QueueModel[]? queue, string stationOwner, string? lastModified)
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
