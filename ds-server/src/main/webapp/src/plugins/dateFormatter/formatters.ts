import dayjs from 'dayjs'

export function format (v: string | Date | number, pattern = 'YYYY-MM-DD HH:mm:ss'): string {
  const day = dayjs(v)
  return day.isValid() ? day.format(pattern) : ''
}

export function formatDate (v: string | Date | number, pattern = 'YYYY-MM-DD'): string {
  return format(v, pattern)
}

export function formatTime (v: string | Date | number, pattern = 'HH:mm:ss'): string {
  return format(v, pattern)
}

export {
  format as formatDatetime
}
