global main
main:
call func
not eax
ret
global func
func:
mov eax, 25
ret