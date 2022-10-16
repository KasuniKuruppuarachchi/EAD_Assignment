using FuelQueManagement_Service.Models;
using MongoDB.Driver;

namespace FuelQueManagement_Service
{
    public class DbConnection
    {
        public DbConnection(IConfiguration config)
        {
            var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
            var _db = Client.GetDatabase("FuelQueue");

            var collection = _db.GetCollection<FuelStationModel>("FuelStation");

            var station = new FuelStationModel{
                Name = "PMC",
                Location = "Makola"
            };


        }
    }
}
