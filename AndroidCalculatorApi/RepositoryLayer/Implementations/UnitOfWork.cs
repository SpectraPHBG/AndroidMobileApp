using Data_Layer.Entities;
using RepositoryLayer.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data_Layer.Context
{
    public class UnitOfWork : IDisposable
    {
        private AndroidCalculatorDBContext context = new AndroidCalculatorDBContext();
        private GenericRepository<CalculatorEquation> equationRepository;

        public GenericRepository<CalculatorEquation> EquationRepository
        {
            get
            {

                if (this.equationRepository == null)
                {
                    this.equationRepository = new GenericRepository<CalculatorEquation>(context);
                }
                return equationRepository;
            }
        }

        public void Save()
        {
            context.SaveChanges();
        }

        private bool disposed = false;

        protected virtual void Dispose(bool disposing)
        {
            if (!this.disposed)
            {
                if (disposing)
                {
                    context.Dispose();
                }
            }
            this.disposed = true;
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }
    }
}
