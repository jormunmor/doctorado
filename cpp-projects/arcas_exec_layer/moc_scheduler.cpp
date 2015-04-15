/****************************************************************************
** Meta object code from reading C++ file 'scheduler.h'
**
** Created: Tue Apr 14 22:21:17 2015
**      by: The Qt Meta Object Compiler version 63 (Qt 4.8.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "scheduler.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'scheduler.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 63
#error "This file was generated using the moc from 4.8.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_VehicleScheduler[] = {

 // content:
       6,       // revision
       0,       // classname
       0,    0, // classinfo
       2,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       1,       // signalCount

 // signals: signature, parameters, type, tag, flags
      46,   18,   17,   17, 0x05,

 // slots: signature, parameters, type, tag, flags
      98,   87,   17,   17, 0x0a,

       0        // eod
};

static const char qt_meta_stringdata_VehicleScheduler[] = {
    "VehicleScheduler\0\0vehicleID,operationID,state\0"
    "stateChanged(int,int,Ui::OperationState)\0"
    "operations\0schedule(QVector<QStringList>&)\0"
};

void VehicleScheduler::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        Q_ASSERT(staticMetaObject.cast(_o));
        VehicleScheduler *_t = static_cast<VehicleScheduler *>(_o);
        switch (_id) {
        case 0: _t->stateChanged((*reinterpret_cast< const int(*)>(_a[1])),(*reinterpret_cast< const int(*)>(_a[2])),(*reinterpret_cast< const Ui::OperationState(*)>(_a[3]))); break;
        case 1: _t->schedule((*reinterpret_cast< QVector<QStringList>(*)>(_a[1]))); break;
        default: ;
        }
    }
}

const QMetaObjectExtraData VehicleScheduler::staticMetaObjectExtraData = {
    0,  qt_static_metacall 
};

const QMetaObject VehicleScheduler::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_VehicleScheduler,
      qt_meta_data_VehicleScheduler, &staticMetaObjectExtraData }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &VehicleScheduler::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *VehicleScheduler::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *VehicleScheduler::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_VehicleScheduler))
        return static_cast<void*>(const_cast< VehicleScheduler*>(this));
    return QObject::qt_metacast(_clname);
}

int VehicleScheduler::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 2)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 2;
    }
    return _id;
}

// SIGNAL 0
void VehicleScheduler::stateChanged(const int & _t1, const int & _t2, const Ui::OperationState & _t3)
{
    void *_a[] = { 0, const_cast<void*>(reinterpret_cast<const void*>(&_t1)), const_cast<void*>(reinterpret_cast<const void*>(&_t2)), const_cast<void*>(reinterpret_cast<const void*>(&_t3)) };
    QMetaObject::activate(this, &staticMetaObject, 0, _a);
}
QT_END_MOC_NAMESPACE
