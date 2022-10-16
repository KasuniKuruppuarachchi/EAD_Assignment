using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace FuelQueManagement_Service.Controllers
{
    public class QueueController : Controller
    {
        // GET: QueueController
        public ActionResult Index()
        {
            return View();
        }

        // GET: QueueController/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: QueueController/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: QueueController/Create
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

        // GET: QueueController/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: QueueController/Edit/5
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

        // GET: QueueController/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        // POST: QueueController/Delete/5
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
