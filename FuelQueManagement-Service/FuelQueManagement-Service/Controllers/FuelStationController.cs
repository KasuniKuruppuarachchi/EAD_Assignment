using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace FuelQueManagement_Service.Controllers
{
    public class FuelStationController : Controller
    {
        // GET: FuelStationController
        public ActionResult Index()
        {
            return View();
        }

        // GET: FuelStationController/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: FuelStationController/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: FuelStationController/Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(IFormCollection collection)
        {
            try
            {
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

        // GET: FuelStationController/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: FuelStationController/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(int id, IFormCollection collection)
        {
            try
            {
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

        // GET: FuelStationController/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        // POST: FuelStationController/Delete/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Delete(int id, IFormCollection collection)
        {
            try
            {
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }
    }
}
