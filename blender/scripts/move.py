import bpy

# Creates an operator. This operator moves the selected object
# one unity along the X axis.
class MoveOperator(bpy.types.Operator):
    bl_idname = "object.move_operator"
    bl_label = "Move Operator"

    def execute(self, context):
        context.active_object.location.x += 1.0
        # print output goes to the system console, not to the UI
        print("The active object was moved 1 unity along the X axis")
        return {'FINISHED'}

# Registers the operator
def register():
    bpy.utils.register_class(MoveOperator)

if __name__ == "__main__":
    register()
 
# Run the script to register it in Blender, then press space in the 3D view
# and search the operator in the text box. Click on it to execute. The selected
# object must move along the X axis.
