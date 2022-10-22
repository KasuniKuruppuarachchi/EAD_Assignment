using FuelQueManagement_Service.Models;
using FuelQueManagement_Service.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Bson;
using MongoDB.Driver;

namespace FuelQueManagement_Service.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class QueueController : Controller
    {
        // Declearing the fuel station service instance
        private readonly QueueService _queueService;
        private readonly FuelStationService _fuelStationService;
        public QueueController(QueueService queueService, FuelStationService fuelStationService)
        {
            _queueService = queueService;
            _fuelStationService = fuelStationService;

        }

        // This is required to create a queue object
        [HttpPost]
        public async Task<FuelStationModel> Create(QueueModel request)
        {
            try
            {
                var res = await _queueService.Create(request);
                return res;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        // This is required to delete a queue object
        [HttpDelete]
        public async Task<FuelStationModel> Delete(string fuelType, string stationId, string queueId, bool aquired)
        {
            try
            {
                if (aquired)
                {
                    QueueModel queue = new QueueModel();
                    var station = await _fuelStationService.GetStationByQueueId(queueId);
                    _queueService.UpdateQueueHistory(stationId, station.Queue[0]);
                }

                var res = await _queueService.Delete(fuelType, stationId, queueId);
                return res;

            }
            catch (Exception e)
            {
                return null;
            }
        }

        [HttpGet]
        [Route("Getstation")]
        public async Task<FuelStationModel> Getstation(string queueId)
        {
            return await _fuelStationService.GetStationByQueueId(queueId);
        }

    }

}
